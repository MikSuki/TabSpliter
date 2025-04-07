package com.miksuki.tabspliter.event

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.miksuki.tabspliter.service.FileRecorder
import com.miksuki.tabspliter.service.TabManager

class FileListener : FileEditorManagerListener {
    lateinit var project: Project

    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        this.project = event.manager.project

        event.newFile?.let {
            FileRecorder.updateLastUsedTime(it, System.currentTimeMillis())
        }
    }

    override fun fileClosed(
        source: FileEditorManager,
        file: VirtualFile,
    ) {
        super.fileClosed(source, file)

        val tabManager = project.getService(TabManager::class.java)
        tabManager.focusTargetWindow(file.url)
    }
}
