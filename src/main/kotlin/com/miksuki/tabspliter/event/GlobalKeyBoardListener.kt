package com.miksuki.tabspliter.event

import com.intellij.openapi.project.Project
import com.miksuki.tabspliter.service.TabManager
import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.KeyEvent

object GlobalKeyBoardListener {
    fun register(project: Project) {
        Toolkit.getDefaultToolkit().addAWTEventListener(
            { event ->
                if (event is KeyEvent) {
                    val keyEvent: KeyEvent = event as KeyEvent
                    if (keyEvent.id == KeyEvent.KEY_RELEASED) {
                        if (keyEvent.keyCode == KeyEvent.VK_CONTROL) {
                            val tabManager = project.getService(TabManager::class.java)
                            tabManager.finishSwitchingTab()
                        }
                    }
                }
            },
            AWTEvent.KEY_EVENT_MASK,
        )
    }
}
