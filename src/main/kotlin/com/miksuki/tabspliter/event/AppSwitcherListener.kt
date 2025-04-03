package com.miksuki.tabspliter.event

import com.intellij.openapi.application.ApplicationActivationListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.wm.IdeFrame
import com.intellij.util.messages.MessageBusConnection

interface AppSwitchListener {
    fun onIdeDeactivated()
}

object AppSwitchDetector {
    private var isIdeActive = true
    private lateinit var listener: AppSwitchListener

    init {
        val connection: MessageBusConnection =
            ApplicationManager
                .getApplication()
                .messageBus
                .connect()

        connection.subscribe(
            ApplicationActivationListener.TOPIC,
            object : ApplicationActivationListener {
                override fun applicationActivated(ideFrame: IdeFrame) {
                    isIdeActive = true
                }

                override fun applicationDeactivated(ideFrame: IdeFrame) {
                    isIdeActive = false
                }
            },
        )

        ApplicationManager
            .getApplication()
            .messageBus
            .connect()
            .subscribe(
                ApplicationActivationListener.TOPIC,
                object : ApplicationActivationListener {
                    override fun applicationActivated(ideFrame: IdeFrame) { }

                    override fun applicationDeactivated(ideFrame: IdeFrame) {
                        listener.onIdeDeactivated()
                    }
                },
            )
    }

    fun initListener(listener: AppSwitchListener) {
        this.listener = listener
    }
}
