package com.miksuki.tabspliter.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBList
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Point
import java.awt.Toolkit
import javax.swing.BorderFactory
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JPanel

object TabSwitcherPopup {
    private lateinit var popup: JBPopup
    private var panelWidth: Int = 500
    private var panelHeight: Int = 300
    private var panelPosX: Int = 0
    private var panelPosY: Int = 0
    private lateinit var list: JBList<String>

    private fun createPopup(fileNames: List<String>): JBPopup {
        val closeButton =
            JButton(AllIcons.Actions.Close).apply {
                isContentAreaFilled = false
                addActionListener {
                    close()
                }
            }

        val listModel = DefaultListModel<String>()

        list = JBList(listModel)

        val titlePanel =
            JPanel(BorderLayout()).apply {
                preferredSize = JBUI.size(panelWidth, 30)
                background = JBColor.LIGHT_GRAY
                add(closeButton, BorderLayout.EAST)
            }

        val panel =
            JPanel().apply {
                preferredSize = JBUI.size(panelWidth, panelHeight)
                background = JBColor.WHITE
                border = BorderFactory.createLineBorder(JBColor.GRAY)

                add(titlePanel)
                add(list)
            }

        val popup =
            JBPopupFactory
                .getInstance()
                .createComponentPopupBuilder(panel, null)
                .createPopup()
        listModel.addAll(fileNames)

        return popup
    }

    fun setSelectedItem(index: Int) {
        list.selectedIndex = index
    }

    fun show(fileNames: List<String>) {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        panelWidth = (screenSize.width * 0.5).toInt()
        panelHeight = (screenSize.height * 0.3).toInt()
        panelPosX = (screenSize.width - panelWidth) / 2
        panelPosY = 0

        popup = createPopup(fileNames)
        popup.show(RelativePoint(Point(panelPosX, panelPosY)))
    }

    fun close() {
        popup.cancel()
    }
}
