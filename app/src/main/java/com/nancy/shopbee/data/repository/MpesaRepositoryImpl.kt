package com.nancy.shopbee.data.repository

import android.util.Base64
import android.util.Log
import com.nancy.shopbee.data.remote.MpesaApiService
import com.nancy.shopbee.data.remote.MpesaConfig
import com.nancy.shopbee.domain.models.mpesa.STKPushResponse
import com.nancy.shopbee.domain.models.mpesa.StkPushRequest
import com.nancy.shopbee.domain.repository.MpesaRepository
import com.nancy.shopbee.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MpesaRepositoryImpl
    @Inject
    constructor(
        private val mpesaApiService: MpesaApiService,
        private val consumerKey: String,
        private val consumerSecret: String,
        private val callbackUrl: String,
        private val shortCode: String,
        private val passKey: String,
    ) : MpesaRepository {
        override suspend fun initiateStkPush(
            phoneNumber: String,
            amount: String,
            accountRef: String,
            description: String,
        ): Resource<STKPushResponse> {
            return try {
                val credentials =
                    Base64.encodeToString(
                        "$consumerKey:$consumerSecret".toByteArray(),
                        Base64.NO_WRAP,
                    )

                val tokenResponse =
                    mpesaApiService.getAccessToken(
                        "Basic $credentials",
                    )

                val accessToken = "Bearer ${tokenResponse.accessToken}"

                val timestamp =
                    SimpleDateFormat(
                        "yyyyMMddHHmmss",
                        Locale.getDefault(),
                    ).format(Date())

                val rawPassword = "$shortCode$passKey$timestamp"

                val password =
                    Base64.encodeToString(
                        rawPassword.toByteArray(),
                        Base64.NO_WRAP,
                    )

                val stkPushRequest =
                    StkPushRequest(
                        businessShortCode = shortCode,
                        password = password,
                        timestamp = timestamp,
                        transactionType = MpesaConfig.TRANSACTION_TYPE,
                        amount = amount,
                        partyA = phoneNumber,
                        partyB = shortCode,
                        phoneNumber = phoneNumber,
                        callBackURL = callbackUrl,
                        accountReference = accountRef,
                        transactionDesc = description,
                    )

                val response =
                    mpesaApiService.initiateStkPush(
                        accessToken,
                        stkPushRequest,
                    )

                Resource.Success(response)
            } catch (e: IOException) {
                Log.e("MpesaRepository", "Network error", e)
                Resource.Error("Please check your internet connection.")
            } catch (e: HttpException) {
                Log.e("MpesaRepository", "HTTP error", e)
                Resource.Error("Server error occurred.")
            } catch (e: IllegalStateException) {
                Log.e("MpesaRepository", "State error", e)
                Resource.Error(
                    e.localizedMessage ?: "Unexpected application state.",
                )
            } catch (e: Exception) {
                Log.e("MpesaRepository", "Unknown error", e)
                Resource.Error("Unknown error occurred.")
            }
        }
    }
