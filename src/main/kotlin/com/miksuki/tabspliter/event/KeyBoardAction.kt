package com.miksuki.tabspliter.event

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.miksuki.tabspliter.service.TabManager
import com.miksuki.tabspliter.type.Direction

class PrepareAndSwitchTabNext : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.NEXT, true)
    }
}

class PrepareAndSwitchTabPrevious : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.PREVIOUS, true)
    }
}

class SwitchTabNext : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.NEXT, false)
    }
}

class SwitchTabPrevios : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.switchActiveTabFile(Direction.PREVIOUS, false)
    }
}

class MoveFileRight : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.moveFileRight()
    }
}

class MoveFileLeft : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.moveFileLeft()
    }
}

class MoveToFisrtTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(0)
    }
}

class MoveToSecondTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(1)
    }
}

class MoveToThirdTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(2)
    }
}

class MoveToFourthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(3)
    }
}
class MoveToFifthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(4)
    }
}
class MoveToSixthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(5)
    }
}
class MoveToSeventhTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(6)
    }
}
class MoveToEighthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(7)
    }
}
class MoveToNinthTab : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.selectTab(8)
    }
}

class ToggleCurrentTabSize : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TabManager.toggleCurrentTabSize()
    }
}
