package com.miksuki.tabspliter.event

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.miksuki.tabspliter.TabManager

class SwitchTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile()
        println("switch tab")
    }
}

class MoveToFisrtTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(0)
        println("move to tab-1")
    }
}

class MoveToSecondTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(1)
        println("move to tab-2")
    }
}

class MoveToThirdTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(2)
        println("move to tab-3")
    }
}

class MoveFileRight : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.moveFileRight()
        println("move tab right")
    }
}

