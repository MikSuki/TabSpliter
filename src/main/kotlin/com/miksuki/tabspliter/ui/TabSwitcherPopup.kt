package com.miksuki.tabspliter.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Point
import java.awt.Toolkit
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

object TabSwitcherPopup {
    private val closeButton =
        JButton(AllIcons.Actions.Close).apply {
            isContentAreaFilled = false
            addActionListener {
                close()
            }
        }

    private val titlePanel =
        JPanel(BorderLayout()).apply {
            preferredSize = JBUI.size(300, 30)
            background = JBColor.LIGHT_GRAY
            add(closeButton, BorderLayout.EAST)
        }

    private val panel =
        JPanel().apply {
            preferredSize = JBUI.size(300, 200)
            background = JBColor.WHITE
            border = BorderFactory.createLineBorder(JBColor.GRAY)

            add(titlePanel)
            add(JLabel("title", SwingConstants.CENTER))
        }



    private val popup =
        JBPopupFactory
            .getInstance()
            .createComponentPopupBuilder(panel, null)
            .createPopup()

    fun show() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val x = (screenSize.width - panel.getPreferredSize().width) / 2
        val y = (screenSize.height - panel.getPreferredSize().height) / 4

        popup.show(RelativePoint(Point(x, y)))
    }

    fun close() {
        popup.cancel()
    }
}
