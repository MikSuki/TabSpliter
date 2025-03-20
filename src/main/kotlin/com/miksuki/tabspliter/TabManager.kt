package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx
    var currentPos = CyclicCounter(4)

    fun init(project: Project) {
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    fun selectTab(index: Int) {
        if (fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size) {
            fileEditorManagerEx.currentWindow = fileEditorManagerEx.windows[index]
        }
    }

    fun switchActiveTabFile() {
        val fileList = fileEditorManagerEx.splitters.openFileList

        // TODO: need stpre last used time for files, it is not stored in intellij...
        fileList.sortedByDescending { it.timeStamp }
        fileList.get(0).modificationStamp


        if(currentPos.get() == 3)
            fileEditorManagerEx.openFile(fileList[currentPos.get()])

        fileList.mapIndexed { index, file ->

            if (currentPos.get() == index) {
                println(" * $index ${file.name}")
            } else {
                println("   $index ${file.name}")
            }

        }

        currentPos.add()
    }
}
