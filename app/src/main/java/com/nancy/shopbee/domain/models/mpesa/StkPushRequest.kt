package com.nancy.shopbee.domain.models.mpesa

data class STKPushRequest(
    val BusinessShortCode: String,
    val Password: String,
    val Timestamp: String,
    val TransactionType: String = "CustomerPayBillOnline",
    val Amount: String,
    val PartyA: String, // phone number
    val PartyB: String, // The Shortcode
    val PhoneNumber: String,
    val CallBackURL: String,
    val AccountReference: String,
    val TransactionDesc: String
)

