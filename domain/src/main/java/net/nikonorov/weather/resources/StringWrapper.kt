package net.nikonorov.weather.resources

/**
 * Domain representation of a string resource.
 */
sealed class StringWrapper {
    data class StringResourceWrapper(val stringId: Strings, val args: List<Any> = emptyList()) :
        StringWrapper()

    companion object {
        fun from(stringId: Strings, vararg args: Any): StringWrapper {
            return StringResourceWrapper(stringId, args.asList())
        }
    }
}
