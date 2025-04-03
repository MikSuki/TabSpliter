package com.miksuki.tabspliter

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.miksuki.tabspliter.type.Direction
import com.miksuki.tabspliter.ui.TabSwitcherPopup
import com.miksuki.tabspliter.utils.CyclicCounter
import com.miksuki.tabspliter.utils.Utils
import java.awt.Point
import javax.swing.JSplitPane
import javax.swing.SwingUtilities

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx
    private var switchingPos = CyclicCounter(0)
    private var isSwitching = false
    private val tabNeedFocusAfterFileClosed: HashMap<String, () -> Unit> = HashMap()

    var isInit = false

    fun init(project: Project) {
        isInit = true
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    private fun initSwitchingTab(size: Int) {
        val fileList = getActiveTabLastUsedList()
        switchingPos = CyclicCounter(size)
        isSwitching = true
        TabSwitcherPopup.show(fileList)
    }

    fun stopSwitchingTab() {
        if (!isSwitching) {
            return
        }

        TabSwitcherPopup.close()
        isSwitching = false
    }

    fun finishSwitchingTab(index: Int? = null) {
        if (!isSwitching) {
            return
        }
        val pos = index ?: switchingPos.get()
        val fileList = getActiveTabLastUsedList()
        val currentWindow = fileEditorManagerEx.currentWindow
        fileEditorManagerEx.openFile(fileList[pos])
        fileEditorManagerEx.currentWindow = currentWindow
        TabSwitcherPopup.close()
        isSwitching = false
    }

    fun selectTab(index: Int) {
        if (fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size) {
            val sortedWindows = getSortedWindows()
            val targetWindow = sortedWindows[index]
            if (fileEditorManagerEx.currentWindow == targetWindow) {
                return
            }
            targetWindow.setAsCurrentWindow(true)
            val appWidth = Utils.getAppSize().width
            val needMaximize =
                sortedWindows
                    .map { it.tabbedPane.component.width }
                    // it can be enhanced, if we can get the value of "MaximizeEditorInSplit" from intellij
                    .any { it > appWidth / 2 }

            if (needMaximize) {
                toggleCurrentTabSize()
            }
        }
    }

    fun switchActiveTabFile(
        direction: Direction,
        canInitSwitcher: Boolean,
    ) {
        val fileList = getActiveTabLastUsedList()

        if (!isSwitching) {
            if (!canInitSwitcher) {
                return
            }
            initSwitchingTab(fileList.size)
        }

        when (direction) {
            Direction.NEXT -> switchingPos.add()
            Direction.PREVIOUS -> switchingPos.sub()
        }

        TabSwitcherPopup.setSelectedItem(switchingPos.get())

        fileList.mapIndexed { index, file ->
            if (switchingPos.get() == index) {
                println(" * $index ${file.name}")
            } else {
                println("   $index ${file.name}")
            }
        }
    }

    private fun getActiveTabLastUsedList(): List<VirtualFile> {
        val fileList = fileEditorManagerEx.splitters.currentWindow?.fileList ?: listOf()
        return fileList
            .map { it to FileRecorder.getLastUsedTime(it) }
            .sortedByDescending { it.second }
            .map { it.first }
    }

    private fun getSortedWindows(): List<EditorWindow> =
        fileEditorManagerEx.windows
            .map {
                val location = Point(0, 0)
                SwingUtilities.convertPointToScreen(location, it.tabbedPane.component)
                location to it
            }.sortedBy { it.first.x }
            .map { it.second }

    fun moveFileRight() {
        val currentFile = fileEditorManagerEx.currentFile ?: return
        val currentWindow = fileEditorManagerEx.currentWindow ?: return
        val sortedWindows = getSortedWindows()
        val posInWindows = sortedWindows.indexOf(currentWindow)
        val isRightMost = posInWindows == sortedWindows.size - 1

        when (true) {
            isRightMost -> {
                if (currentWindow.fileList.size > 1 /* otherwise, it will no need to move*/) {
                    currentWindow.split(JSplitPane.HORIZONTAL_SPLIT, true, currentFile, true, true)
                        ?: throw Exception("move file left error :(")
                    fileEditorManagerEx.closeFile(currentFile, currentWindow)
                }
            }
            else -> {
                val targetPos = posInWindows + 1
                val targetWindow = sortedWindows[targetPos]
                val filePath = currentFile.url

                val focusTargetEditor: () -> Unit = {
                    ApplicationManager.getApplication().invokeLater {
                        targetWindow.setAsCurrentWindow(true)
                        fileEditorManagerEx.openFile(currentFile, true)
                    }
                    targetWindow.setAsCurrentWindow(true)
                }
                tabNeedFocusAfterFileClosed[currentFile.url] = focusTargetEditor

                fileEditorManagerEx.closeFile(currentFile, currentWindow)
            }
        }
    }

    fun moveFileLeft() {
        val currentFile = fileEditorManagerEx.currentFile ?: return
        val currentWindow = fileEditorManagerEx.currentWindow ?: return
        val sortedWindows = getSortedWindows()
        val posInWindows = sortedWindows.indexOf(currentWindow)
        val isLeftMost = posInWindows == 0

        when (true) {
            isLeftMost -> {
                if (currentWindow.fileList.size > 1 /* otherwise, it will no need to move*/) {
                    currentWindow.split(JSplitPane.HORIZONTAL_SPLIT, true, currentFile, true, false)
                        ?: throw Exception("move file left error :(")
                    fileEditorManagerEx.closeFile(currentFile, currentWindow)
                }
            }
            else -> {
                val targetPos = posInWindows - 1
                val targetWindow = sortedWindows[targetPos]
                val focusTargetEditor: () -> Unit = {
                    ApplicationManager.getApplication().invokeLater {
                        targetWindow.setAsCurrentWindow(true)
                        fileEditorManagerEx.openFile(currentFile, true)
                    }
                }
                tabNeedFocusAfterFileClosed[currentFile.url] = focusTargetEditor
                fileEditorManagerEx.closeFile(currentFile, currentWindow)
                targetWindow.setAsCurrentWindow(true)
            }
        }
    }

    fun focusTargetWindow(url: String) {
        if (tabNeedFocusAfterFileClosed[url] != null) {
            val focusTargetTab = tabNeedFocusAfterFileClosed[url]
            if (focusTargetTab != null) {
                focusTargetTab()
            }
            tabNeedFocusAfterFileClosed.remove(url)
        }
    }

    fun toggleCurrentTabSize() {
        val actionManager = ActionManager.getInstance()
        val action = actionManager.getAction("MaximizeEditorInSplit")
        actionManager.tryToExecute(
            action,
            null,
            null,
            null,
            false,
        )
    }
}
