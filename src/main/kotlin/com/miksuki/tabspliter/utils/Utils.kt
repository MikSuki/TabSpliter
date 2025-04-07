package com.miksuki.tabspliter.utils

import com.intellij.ide.ui.UISettings
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import java.awt.Dimension
import java.awt.Toolkit

@Service(Service.Level.PROJECT)
class Utils(
    private val project: Project,
) {
    fun getProjectPath() = project.basePath

    fun getAppSize(): Dimension {
        val size =
            WindowManager
                .getInstance()
                .getIdeFrame(project)
                ?.component
                ?.size
                ?: Toolkit.getDefaultToolkit().screenSize
        return size
    }

    fun getFontSize() = UISettings.getInstance().fontSize
}
