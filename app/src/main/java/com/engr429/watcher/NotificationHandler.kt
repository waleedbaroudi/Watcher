package com.engr429.watcher

import com.google.firebase.messaging.RemoteMessage

object NotificationHandler {
    private var delegate: NotificationDelegate? = null

    fun setDelegate(delegate: NotificationDelegate) {
        this.delegate = delegate
    }

    fun onNotificationReceived(message: RemoteMessage) {
        delegate?.handleNotification(message.data)
    }
}