package com.miksuki.tabspliter

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.miksuki.tabspliter.event.GlobalKeyBoardListener

class ProjectStartup : ProjectActivity {
    override suspend fun execute(project: Project) {
        TabManager.init(project)
        GlobalKeyBoardListener.register()
    }
}
