package net.nikonorov.weather

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LocationSelectionKakaoTest {

    @Rule
    @JvmField
    val chain = RuleChain
        .outerRule(ActivityScenarioRule(MainActivity::class.java)).around(DispatcherIdlerRule())

    @Test
    fun testContentItemsRecyclerView() {
        onScreen<LocationSelectionScreen> {
            locationsList {
                isVisible()
                firstChild<LocationSelectionScreen.LocationItem> {
                    isVisible()
                    label { hasText("Sydney") }
                    click()
                }
            }
            nextButton.click()
        }
        onScreen<ForecastScreen> {
            screenView {
                isVisible()
            }
            locationName {
                hasText("Sydney")
            }
        }
    }
}
