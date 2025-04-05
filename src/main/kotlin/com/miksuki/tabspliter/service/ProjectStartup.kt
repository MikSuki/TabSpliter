package com.miksuki.tabspliter.service

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.miksuki.tabspliter.event.AppSwitchDetector
import com.miksuki.tabspliter.event.AppSwitchListener
import com.miksuki.tabspliter.event.GlobalKeyBoardListener
import com.miksuki.tabspliter.utils.GlobalVariable

class ProjectStartup :
    ProjectActivity,
    AppSwitchListener {
    override suspend fun execute(project: Project) {
        GlobalVariable.project = project
        TabManager.init(project)
        GlobalKeyBoardListener.register()
        AppSwitchDetector.initListener(this)
    }

    override fun onIdeDeactivated() {
        TabManager.stopSwitchingTab()
    }
}
