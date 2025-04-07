package com.miksuki.tabspliter.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.miksuki.tabspliter.event.AppSwitchDetector
import com.miksuki.tabspliter.event.AppSwitchListener
import com.miksuki.tabspliter.event.GlobalKeyBoardListener

class ProjectStartup :
    ProjectActivity,
    AppSwitchListener {
    lateinit var project: Project

    override suspend fun execute(project: Project) {
        this.project = project
        GlobalKeyBoardListener.register(project)
        AppSwitchDetector.initListener(this)
    }

    override fun onIdeDeactivated() {
        val tabManager = project.getService(TabManager::class.java)
        tabManager.stopSwitchingTab()
    }
}
