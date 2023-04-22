package net.nikonorov.weather.screen

import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import net.nikonorov.weather.R

class ForecastScreen : Screen<ForecastScreen>() {
    val screenView = KView { withId(R.id.forecast_fragment) }
    val locationName = KTextView { withId(R.id.location_name_view) }
}
