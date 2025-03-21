package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx
    var currentPos = CyclicCounter(4)

    var isInit = false

    fun init(project: Project) {
        isInit = true
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    fun selectTab(index: Int) {
        if (fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size) {
            fileEditorManagerEx.currentWindow = fileEditorManagerEx.windows[index]
        }
    }

    fun switchActiveTabFile() {
        val fileList = getActiveTabLastUsedList()

        if (currentPos.get() == 3) {
            fileEditorManagerEx.openFile(fileList[currentPos.get()])
        }

        fileList.mapIndexed { index, file ->

            if (currentPos.get() == index) {
                println(" * $index ${file.name}")
            } else {
                println("   $index ${file.name}")
            }
        }

        currentPos.add()
    }

    fun getActiveTabLastUsedList(): List<VirtualFile> {
        val fileList = fileEditorManagerEx.splitters.openFileList
        return fileList
            .map { it to FileRecorder.getLastUsedTime(it) }
            .sortedByDescending { it.second }
            .map { it.first }
    }
}
