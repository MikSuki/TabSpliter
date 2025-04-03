package com.miksuki.tabspliter

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.miksuki.tabspliter.event.AppSwitchDetector
import com.miksuki.tabspliter.event.AppSwitchListener
import com.miksuki.tabspliter.event.GlobalKeyBoardListener

class ProjectStartup :
    ProjectActivity,
    AppSwitchListener {
    override suspend fun execute(project: Project) {
        TabManager.init(project)
        GlobalKeyBoardListener.register()
        AppSwitchDetector.initListener(this)
    }

    override fun onIdeDeactivated() {
        TabManager.stopSwitchingTab()
    }
}
