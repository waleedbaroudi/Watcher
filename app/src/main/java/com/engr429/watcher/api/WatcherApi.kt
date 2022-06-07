package com.engr429.watcher.api

object WatcherApi {
    val instance: IWatcherApi by lazy { IWatcherApi.create() }
}