package com.nancy.shopbee.domain.repository

import com.nancy.shopbee.domain.models.mpesa.STKPushResponse
import com.nancy.shopbee.utils.Resource

interface MpesaRepository {
    suspend fun initiateStkPush(
        phoneNumber: String,
        amount: String,
        accountRef: String,
        description: String,
    ): Resource<STKPushResponse>
}
