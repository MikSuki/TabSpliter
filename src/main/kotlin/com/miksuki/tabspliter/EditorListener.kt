package com.miksuki.tabspliter

import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx

class EditorListener : EditorFactoryListener {
    override fun editorCreated(event: EditorFactoryEvent) {
        super.editorCreated(event)

        val editor = event.editor
        val project = editor.project!!
        val ex: FileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project)
        val splitters = ex.splitters
        val currentWindow = splitters.currentWindow
        println("currentWindow: $currentWindow")
        val allWindows = splitters.getWindows()
        println("allWindows files: ")
        allWindows.forEach {
            println(it)
        }
        println("allWindows files: ")
        allWindows.forEach {
            println(it)
        }
        println("---------------------------------------")
    }
}
