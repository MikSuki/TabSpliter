package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.miksuki.tabspliter.ui.TabSwitcherPopup
import com.miksuki.tabspliter.utils.CyclicCounter
import javax.swing.JSplitPane

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx
    private var switchingPos = CyclicCounter(0)
    private var isSwitching = false

    var isInit = false

    fun init(project: Project) {
        isInit = true
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    private fun initSwitchingTab(size: Int) {
        val fileList = getActiveTabLastUsedList()
        switchingPos = CyclicCounter(size)
        isSwitching = true
        TabSwitcherPopup.show(fileList.map { it.name })
    }

    fun finishSwitchingTab() {
        if (!isSwitching) {
            return
        }
        val fileList = getActiveTabLastUsedList()
        fileEditorManagerEx.openFile(fileList[switchingPos.get()])
        TabSwitcherPopup.close()
        isSwitching = false
    }

    fun selectTab(index: Int) {
        if (fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size) {
            val sortedWindows = getSortedWindows()
            fileEditorManagerEx.currentWindow = sortedWindows[index]
        }
    }

    fun switchActiveTabFile() {
        val fileList = getActiveTabLastUsedList()

        if (!isSwitching) {
            initSwitchingTab(fileList.size)
        }

        switchingPos.add()

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

    private fun getSortedWindows() =
        fileEditorManagerEx.windows.clone().apply {
            sortBy { it.tabbedPane.component.location.x }
        }

    fun moveFileRight() {
        val currentFile = fileEditorManagerEx.currentFile ?: return
        val currentWindow = fileEditorManagerEx.currentWindow ?: return
        val sortedWindows = getSortedWindows()
        val posInWindows = sortedWindows.indexOf(currentWindow)
        val isRightMost = posInWindows == sortedWindows.size - 1

        when (true) {
            isRightMost -> {
                if (currentWindow.fileList.size > 1 /* otherwise, it will no need to move right*/ ) {
                    fileEditorManagerEx.createSplitter(JSplitPane.HORIZONTAL_SPLIT, null)
                    fileEditorManagerEx.closeFile(currentFile, currentWindow)
                }
            }
            else -> {
                val targetPos = posInWindows + 1
                val targetWindow = sortedWindows[targetPos]

                fileEditorManagerEx.closeFile(currentFile, currentWindow)
                fileEditorManagerEx.openFile(
                    currentFile,
                    targetWindow,
                )
            }
        }
    }
}
