package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.miksuki.tabspliter.ui.TabSwitcherPopup
import com.miksuki.tabspliter.utils.CyclicCounter

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
        switchingPos = CyclicCounter(size)
        isSwitching = true
        TabSwitcherPopup.show()
    }

    fun finishSwitchingTab() {
        if (!isSwitching) {
            return
        }
        val fileList = getActiveTabLastUsedList()
        fileEditorManagerEx.openFile(fileList[switchingPos.get()])
        TabSwitcherPopup.close()
    }

    fun selectTab(index: Int) {
        if (fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size) {
            fileEditorManagerEx.currentWindow = fileEditorManagerEx.windows[index]
        }
    }

    fun switchActiveTabFile() {
        val fileList = getActiveTabLastUsedList()

        if (!isSwitching) {
            initSwitchingTab(fileList.size)
        }

        switchingPos.add()

        fileList.mapIndexed { index, file ->
            if (switchingPos.get() == index) {
                println(" * $index ${file.name}")
            } else {
                println("   $index ${file.name}")
            }
        }

    }

    fun getActiveTabLastUsedList(): List<VirtualFile> {
        val fileList = fileEditorManagerEx.splitters.currentWindow?.fileList ?: listOf()
        return fileList
            .map { it to FileRecorder.getLastUsedTime(it) }
            .sortedByDescending { it.second }
            .map { it.first }
    }
}
