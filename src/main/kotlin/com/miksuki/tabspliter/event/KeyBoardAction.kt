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

class MoveFileRight : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.moveFileRight()
        println("move tab right")
    }
}

class MoveFileLeft : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.moveFileLeft()
        println("move tab left")
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

class MoveToFourthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(3)
        println("move to tab-4")
    }
}
class MoveToFifthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(4)
        println("move to tab-5")
    }
}
class MoveToSixthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(5)
        println("move to tab-6")
    }
}
class MoveToSeventhTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(6)
        println("move to tab-7")
    }
}
class MoveToEighthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(7)
        println("move to tab-8")
    }
}
class MoveToNinthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(8)
        println("move to tab-9")
    }
}
