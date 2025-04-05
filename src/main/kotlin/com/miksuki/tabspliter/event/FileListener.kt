package com.miksuki.tabspliter.event

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import com.miksuki.tabspliter.service.FileRecorder
import com.miksuki.tabspliter.service.TabManager

class FileListener : FileEditorManagerListener {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        event.newFile?.let {
            FileRecorder.updateLastUsedTime(it, System.currentTimeMillis())
        }
    }

    override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
        super.fileClosed(source, file)

        TabManager.focusTargetWindow(file.url)
    }
}
