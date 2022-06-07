package com.engr429.watcher.main

import com.google.firebase.messaging.RemoteMessage

object NotificationHandler {
    private var delegate: NotificationDelegate? = null

    fun setDelegate(delegate: NotificationDelegate) {
        NotificationHandler.delegate = delegate
    }

    fun onNotificationReceived(message: RemoteMessage) {
        delegate?.handleNotification(message.data)
    }
}