package com.miksuki.tabspliter

import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener

class FileListener : FileEditorManagerListener {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        event.newFile?.let {
            FileRecorder.updateLastUsedTime(it, System.currentTimeMillis())
        }

        if(TabManager.isInit)
            TabManager
                .getActiveTabLastUsedList()
                .map { println(it) }
    }
}
