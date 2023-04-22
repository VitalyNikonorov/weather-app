package net.nikonorov.weather.util

import kotlinx.coroutines.Dispatchers
import net.nikonorov.weather.AppDispatchers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestDispatcherRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                val espressoDispatcherMain = EspressoTestDispatcher(Dispatchers.Main)
                val espressoDispatcherIO = EspressoTestDispatcher(Dispatchers.IO)
                val espressoDispatcherDefault =
                    EspressoTestDispatcher(Dispatchers.Default)
                AppDispatchers.Main = espressoDispatcherMain
                AppDispatchers.IO = espressoDispatcherIO
                AppDispatchers.Default = espressoDispatcherDefault
                try {
                    base.evaluate()
                } finally {
                    espressoDispatcherIO.cleanUp()
                    espressoDispatcherDefault.cleanUp()
                    AppDispatchers.resetAll()
                }
            }
        }
}