package com.miksuki.tabspliter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class MoveToFisrtTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        println("move to tab-1")
    }
}

class MoveToSecondTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        println("move to tab-2")
    }
}

class MoveToThirdTab: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        println("move to tab-3")
    }
}
