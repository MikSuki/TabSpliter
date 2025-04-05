package com.miksuki.tabspliter.ui

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBList
import com.miksuki.tabspliter.TabManager
import com.miksuki.tabspliter.utils.Utils
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

object SwitcherList {
    class ListItem(
        val icon: Icon,
        val text: String,
    )

    private lateinit var list: JBList<ListItem>

    fun create(
        files: List<VirtualFile>,
        panelWidth: Int,
        panelHeight: Int,
        fontSize: Int,
    ): JBList<ListItem> {
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
        val listModel = DefaultListModel<ListItem>()

        val filesNeedShowPath =
            files
                .groupBy { it.name }
                .filter { it.value.size > 1 }
                .map { it.key }
                .toSet()

        listModel.addAll(
            files.map {
                ListItem(
                    it.fileType.icon,
                    if (filesNeedShowPath.contains(it.name)) {
                        it.url.let { url ->
                            val projectUrl = Utils.getProjectPath() ?: url
                            val index = url.indexOf(projectUrl)
                            url.substring(index + projectUrl.length)
                        }
                    } else {
                        it.name
                    },
                )
            },
        )
        list =
            JBList(listModel).apply {
                preferredSize.width = panelWidth
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                font = font.deriveFont(fontSize)
                fixedCellHeight = fontSize * 2
                cellRenderer = CellListRenderer()
                addMouseListener(
                    object : MouseAdapter() {
                        override fun mouseClicked(e: MouseEvent) {
                            val index = list.locationToIndex(e.point)
                            if (index >= 0) {
                                TabManager.finishSwitchingTab(index)
                            }
                        }

                        override fun mouseEntered(e: MouseEvent) { }

                        override fun mouseExited(e: MouseEvent) { }
                    },
                )
            }

        return list
    }
}
