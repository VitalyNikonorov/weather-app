package net.nikonorov.weather.presentation.base

import kotlinx.coroutines.flow.Flow

/**
 * Base interface of the MVVM ViewModel
 */
interface BaseViewModel<T> {
    val uiState: Flow<T>
}
