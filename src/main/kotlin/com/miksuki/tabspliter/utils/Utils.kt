package com.miksuki.tabspliter.utils

import com.intellij.openapi.wm.WindowManager
import java.awt.Dimension
import java.awt.Toolkit

object Utils {
    fun getProjectPath() = GlobalVariable.project.basePath

    fun getAppSize(): Dimension {
        val size =
            WindowManager
                .getInstance()
                .getIdeFrame(GlobalVariable.project)
                ?.component
                ?.size
                ?: Toolkit.getDefaultToolkit().screenSize
        return size
    }
}
