package net.nikonorov.weather.model

sealed class LoadableData<out Data, out Error> {

    abstract val data: Data?

    data class Loading<out Data>(override val data: Data?) : LoadableData<Data, Nothing>()
    data class Success<out Data>(override val data: Data) : LoadableData<Data, Nothing>()
    data class Error<out Error>(val error: Error) : LoadableData<Nothing, Error>() {
        override val data: Nothing?
            get() = null
    }
}
