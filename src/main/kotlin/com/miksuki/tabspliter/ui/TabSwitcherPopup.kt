package com.miksuki.tabspliter.ui

import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import com.miksuki.tabspliter.utils.Utils
import java.awt.BorderLayout
import java.awt.Point
import javax.swing.*

object TabSwitcherPopup {
    private lateinit var popup: JBPopup
    private var panelWidth: Int = 500
    private var panelHeight: Int = 300
    private var panelPosX: Int = 0
    private var panelPosY: Int = 0
    private var fontSize: Int = 20
    private var panelPadding: Int = 0
    private lateinit var list: JBList<SwitcherList.ListItem>

    private fun createPopup(files: List<VirtualFile>): JBPopup {
        val closeButton =
            JButton(AllIcons.Actions.Close).apply {
                isContentAreaFilled = false
                addActionListener {
                    close()
                }
            }

        list = SwitcherList.create(files, panelWidth, panelHeight, fontSize)

        val scrollPane =
            JBScrollPane(list).apply {
                preferredSize.width = panelWidth
                border = JBUI.Borders.empty(0, panelPadding)
            }

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
                border = BorderFactory.createLineBorder(JBColor.GRAY, 1, true)
                layout = BorderLayout()

                add(titlePanel, BorderLayout.NORTH)
                add(scrollPane, BorderLayout.CENTER)
            }

        val popup =
            JBPopupFactory
                .getInstance()
                .createComponentPopupBuilder(panel, null)
                .createPopup()

        return popup
    }

    fun setSelectedItem(index: Int) {
        list.selectedIndex = index
        list.scrollRectToVisible(list.getCellBounds(index, index))
    }

    fun show(files: List<VirtualFile>) {
        val appSize = Utils.getAppSize()
        panelWidth = (appSize.width * 0.3).toInt()
        panelHeight = (appSize.height * 0.3).toInt()
        panelPosX = (appSize.width - panelWidth) / 2
        panelPosY = 0
        panelPadding = (panelWidth * 0.05).toInt()
        fontSize = Utils.getFontSize()

        popup = createPopup(files)
        popup.show(RelativePoint(Point(panelPosX, panelPosY)))
    }

    fun close() {
        popup.cancel()
    }
}
