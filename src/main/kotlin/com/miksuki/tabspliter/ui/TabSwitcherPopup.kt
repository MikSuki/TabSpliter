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
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Point
import java.awt.Toolkit
import javax.swing.*

object TabSwitcherPopup {
    class ListItem(
        val icon: Icon,
        val text: String,
    )

    class CellListRenderer : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
            list: JList<out Any>?,
            value: Any?,
            index: Int,
            isSelected: Boolean,
            cellHasFocus: Boolean,
        ): Component {
            icon = (value as ListItem).icon
            text = value.text
            border =
                BorderFactory.createEmptyBorder(
                    (panelHeight * 0.05).toInt(),
                    (panelWidth * 0.1).toInt(),
                    (panelHeight * 0.05).toInt(),
                    (panelWidth * 0.1).toInt(),
                )

            return this
        }
    }

    private lateinit var popup: JBPopup
    private var panelWidth: Int = 500
    private var panelHeight: Int = 300
    private var panelPosX: Int = 0
    private var panelPosY: Int = 0
    private var panelPadding: Int = 0
    private lateinit var list: JBList<ListItem>

    private fun createPopup(files: List<VirtualFile>): JBPopup {
        val closeButton =
            JButton(AllIcons.Actions.Close).apply {
                isContentAreaFilled = false
                addActionListener {
                    close()
                }
            }

        val listModel = DefaultListModel<ListItem>()

        listModel.addAll(files.map { ListItem(it.fileType.icon, it.name) })
        list =
            JBList(listModel).apply {
                preferredSize.width = panelWidth
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                font = font.deriveFont(20f)
                fixedCellHeight = 40
                cellRenderer = CellListRenderer()
            }

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
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        panelWidth = (screenSize.width * 0.3).toInt()
        panelHeight = (screenSize.height * 0.3).toInt()
        panelPosX = (screenSize.width - panelWidth) / 2
        panelPosY = 0
        panelPadding = (panelWidth * 0.05).toInt()

        popup = createPopup(files)
        popup.show(RelativePoint(Point(panelPosX, panelPosY)))
    }

    fun close() {
        popup.cancel()
    }
}
