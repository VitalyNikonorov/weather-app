package net.nikonorov.weather.util

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class EspressoTestDispatcher(private val wrappedDispatcher: CoroutineDispatcher) :
    CoroutineDispatcher() {
    private val counter: CountingIdlingResource =
        CountingIdlingResource("EspressoTestDispatcher:$wrappedDispatcher")

    init {
        IdlingRegistry.getInstance().register(counter)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        counter.increment()
        val wrappedBlock = Runnable {
            try {
                block.run()
            } finally {
                counter.decrement()
            }
        }
        wrappedDispatcher.dispatch(context, wrappedBlock)
    }

    fun cleanUp() {
        IdlingRegistry.getInstance().unregister(counter)
    }
}