package com.miksuki.tabspliter.service

import com.intellij.openapi.vfs.VirtualFile

object FileRecorder {
    private val lastUsedTimes: HashMap<String, Long> = HashMap()

    fun getLastUsedTime(file: VirtualFile) = lastUsedTimes.getOrDefault(file.presentableName, 0)

    fun updateLastUsedTime(file: VirtualFile, timeStamp: Long){
        lastUsedTimes[file.presentableName] = timeStamp
    }
}
