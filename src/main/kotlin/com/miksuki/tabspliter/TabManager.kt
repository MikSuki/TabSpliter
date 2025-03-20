package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.Project

object TabManager {
    private lateinit var fileEditorManagerEx: FileEditorManagerEx

    fun init(project: Project) {
        this.fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
    }

    fun changeCurrentTab(position: Int) {
        fileEditorManagerEx.currentWindow = fileEditorManagerEx.windows[position]
    }
}
