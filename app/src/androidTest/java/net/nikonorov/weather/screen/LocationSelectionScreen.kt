package net.nikonorov.weather

import android.view.View
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

class LocationSelectionScreen : Screen<LocationSelectionScreen>() {
    val nextButton = KView { withText(R.string.next_action_bar_button) }
    val locationsList =
        KRecyclerView(
            builder = { withId(R.id.recycler_view) },
            itemTypeBuilder = { itemType(::LocationItem) }
        )

    class LocationItem(parent: Matcher<View>) : KRecyclerItem<LocationItem>(parent) {
        val label: KTextView = KTextView(parent) { withId(R.id.item_label) }
    }
}
