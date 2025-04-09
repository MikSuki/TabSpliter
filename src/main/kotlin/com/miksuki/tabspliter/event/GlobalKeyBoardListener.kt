package com.miksuki.tabspliter.event

import com.intellij.openapi.project.Project
import com.miksuki.tabspliter.service.TabManager
import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent

object GlobalKeyBoardListener {
    private var isInit = false
    private var currentProject: Project? = null

    fun register() {
        if (isInit) return

        val listener =
            AWTEventListener { event ->
                if (event is KeyEvent) {
                    val keyEvent: KeyEvent = event as KeyEvent
                    if (keyEvent.id == KeyEvent.KEY_RELEASED) {
                        if (keyEvent.keyCode == KeyEvent.VK_CONTROL) {
                            currentProject?.let {
                                val tabManager = it.getService(TabManager::class.java)
                                tabManager.finishSwitchingTab()
                            }
                        }
                    }
                }
            }

        Toolkit.getDefaultToolkit().addAWTEventListener(
            listener,
            AWTEvent.KEY_EVENT_MASK,
        )
        isInit = true
    }

    fun setCurrentProject(project: Project){
        this.currentProject = project
    }
}
