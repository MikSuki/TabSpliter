package com.miksuki.tabspliter.event

import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.miksuki.tabspliter.FileRecorder
import com.miksuki.tabspliter.TabManager

class FileListener : FileEditorManagerListener {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        super.selectionChanged(event)

        event.newFile?.let {
            FileRecorder.updateLastUsedTime(it, System.currentTimeMillis())
        }

        if(TabManager.isInit)
            TabManager.getActiveTabLastUsedList()
                .map { println(it) }
    }
}
