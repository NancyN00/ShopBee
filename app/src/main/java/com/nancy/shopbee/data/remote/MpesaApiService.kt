package com.nancy.shopbee.data.remote

import com.nancy.shopbee.domain.models.mpesa.AccessTokenResponse
import com.nancy.shopbee.domain.models.mpesa.STKPushResponse
import com.nancy.shopbee.domain.models.mpesa.StkPushRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// Two endpoints: OAuth token + STK Push
interface MpesaApiService {
    @GET("oauth/v1/generate?grant_type=client_credentials")
    suspend fun getAccessToken(
        @Header("Authorization") credentials: String
    ): AccessTokenResponse

    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun initiateStkPush(
        @Header("Authorization") token: String,
        @Body request: StkPushRequest
    ): STKPushResponse
}
