package net.nikonorov.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers {
    var Main: CoroutineDispatcher = Dispatchers.Main
    var IO: CoroutineDispatcher = Dispatchers.IO
    var Default: CoroutineDispatcher = Dispatchers.Default

    fun resetAll() {
        Main = Dispatchers.Main
        IO = Dispatchers.IO
        Default = Dispatchers.Default
    }
}
