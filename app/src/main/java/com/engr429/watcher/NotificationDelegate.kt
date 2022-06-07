package com.engr429.watcher

interface NotificationDelegate {
    fun handleNotification(data: Map<String, String>)
}