package net.nikonorov.weather.util

import android.content.Context
import androidx.fragment.app.Fragment
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.resources.resolve

fun Context.getString(wrapper: StringWrapper): String {
    return when (wrapper) {
        is StringWrapper.StringResourceWrapper -> getString(
            wrapper.stringId.resolve(), *wrapper.args.toTypedArray()
        )
    }
}

fun Fragment.getString(wrapper: StringWrapper): String {
    return when (wrapper) {
        is StringWrapper.StringResourceWrapper -> getString(
            wrapper.stringId.resolve(), *wrapper.args.toTypedArray()
        )
    }
}
