package com.miksuki.tabspliter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class SwitchTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile()
        println("switch tab")
    }
}

class MoveToFisrtTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(0)
        println("move to tab-1")
    }
}

class MoveToSecondTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(1)
        println("move to tab-2")
    }
}

class MoveToThirdTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(2)
        println("move to tab-3")
    }
}

