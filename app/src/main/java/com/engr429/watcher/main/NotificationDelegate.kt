package com.engr429.watcher.main

interface NotificationDelegate {
    fun handleNotification(data: Map<String, String>)
}