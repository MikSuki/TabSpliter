package com.miksuki.tabspliter.event

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.miksuki.tabspliter.TabManager
import com.miksuki.tabspliter.type.Direction

class PrepareAndSwitchTabNext : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.NEXT, true)
        println("switch tab next")
    }
}

class PrepareAndSwitchTabPrevious : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.PREVIOUS, true)
        println("switch tab previous")
    }
}

class SwitchTabNext : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.NEXT, false)
        println("switch tab next")
    }
}

class SwitchTabPrevios : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.PREVIOUS, false)
        println("switch tab previous")
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

class ToggleCurrentTabSize : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.toggleCurrentTabSize(e)
        println("maximum tab")
    }
}
