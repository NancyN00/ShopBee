package com.nancy.shopbee.presentation.screens.home.details.mpesa

sealed class MpesaUiState {
    object Idle : MpesaUiState()

    object Loading : MpesaUiState()

    data class Success(val message: String) : MpesaUiState()

    data class Error(val message: String) : MpesaUiState()
}
