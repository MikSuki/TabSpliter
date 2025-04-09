package com.miksuki.tabspliter.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.miksuki.tabspliter.event.AppSwitchDetector
import com.miksuki.tabspliter.event.AppSwitchListener
import com.miksuki.tabspliter.event.GlobalKeyBoardListener

class ProjectStartup :
    ProjectActivity,
    AppSwitchListener {
    var project: Project? = null

    override suspend fun execute(project: Project) {
        this.project = project
        GlobalKeyBoardListener.register()
        AppSwitchDetector.initListener(this)
    }

    override fun onIdeDeactivated() {
        if (this.project == null) return
        val tabManager = project?.getService(TabManager::class.java)
        tabManager?.stopSwitchingTab()
    }
}
