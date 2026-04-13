package com.nancy.shopbee.presentation.screens.home.details.mpesa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nancy.shopbee.domain.repository.MpesaRepository
import com.nancy.shopbee.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MpesaViewModel @Inject constructor(
    private val mpesaRepository: MpesaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MpesaUiState>(MpesaUiState.Idle)
    val uiState: StateFlow<MpesaUiState> = _uiState.asStateFlow()

    fun initiatePay(
        phoneNumber: String,
        amount: String,
        accountRef: String,
        description: String
    ) {
        // Validate phone number format (254XXXXXXXXX)
        val formattedPhone = formatPhoneNumber(phoneNumber)
        if (formattedPhone == null) {
            _uiState.value = MpesaUiState.Error("Enter a valid Safaricom number e.g. 07XXXXXXXX")
            return
        }

        viewModelScope.launch {
            _uiState.value = MpesaUiState.Loading
            val result = mpesaRepository.initiateStkPush(
                phoneNumber = formattedPhone,
                amount = amount,
                accountRef = accountRef,
                description = description
            )
            _uiState.value = when (result) {
                is Resource.Success -> MpesaUiState.Success(
                    result.data?.customerMessage ?: "Check your phone to complete payment."
                )
                is Resource.Error -> MpesaUiState.Error(
                    result.message ?: "Payment failed. Try again."
                )
                is Resource.Loading -> MpesaUiState.Loading
            }
        }
    }

    fun resetState() {
        _uiState.value = MpesaUiState.Idle
    }

    /**
     * Converts 07XXXXXXXX or +254XXXXXXXXX to 254XXXXXXXXX
     * Returns null if invalid.
     */
    private fun formatPhoneNumber(phone: String): String? {
        val cleaned = phone.trim().replace(" ", "").replace("-", "")
        return when {
            cleaned.startsWith("254") && cleaned.length == 12 -> cleaned
            cleaned.startsWith("+254") && cleaned.length == 13 -> cleaned.removePrefix("+")
            cleaned.startsWith("07") && cleaned.length == 10 -> "254${cleaned.removePrefix("0")}"
            cleaned.startsWith("01") && cleaned.length == 10 -> "254${cleaned.removePrefix("0")}"
            else -> null
        }
    }
}
