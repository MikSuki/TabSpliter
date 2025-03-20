package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx

    fun init(project: Project) {
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    fun selectTab(index: Int) {
        if(fileEditorManagerEx.windows.size in 1..fileEditorManagerEx.windows.size)
            fileEditorManagerEx.currentWindow = fileEditorManagerEx.windows[index]
    }
}
