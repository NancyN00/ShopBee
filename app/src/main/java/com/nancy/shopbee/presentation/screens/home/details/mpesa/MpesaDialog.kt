package com.nancy.shopbee.presentation.screens.home.details.mpesa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Locale

data class MpesaPaymentContentArgs(
    val productName: String,
    val amount: Double,
    val phoneNumber: String,
    val uiState: MpesaUiState,
    val onPhoneChange: (String) -> Unit,
    val onDismiss: () -> Unit,
    val onRetry: () -> Unit,
    val onPayClick: () -> Unit,
)

@Composable
fun MpesaPaymentDialog(
    productName: String,
    amount: Double,
    onDismiss: () -> Unit,
    viewModel: MpesaViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            viewModel.resetState()
            onDismiss()
        },
    ) {
        MpesaPaymentContent(
            MpesaPaymentContentArgs(
                productName = productName,
                amount = amount,
                phoneNumber = phoneNumber,
                uiState = uiState,
                onPhoneChange = { phoneNumber = it },
                onDismiss = {
                    viewModel.resetState()
                    onDismiss()
                },
                onRetry = {
                    viewModel.resetState()
                },
                onPayClick = {
                    viewModel.initiatePay(
                        phoneNumber = phoneNumber,
                        amount = amount.toInt().toString(),
                        accountRef = "ShopBee",
                        description = "Payment for $productName",
                    )
                },
            ),
        )
    }
}

@Composable
private fun MpesaPaymentContent(args: MpesaPaymentContentArgs) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MpesaHeader(
                productName = args.productName,
                amount = args.amount,
            )

            when (args.uiState) {
                is MpesaUiState.Success -> {
                    SuccessContent(
                        message = (args.uiState as MpesaUiState.Success).message,
                        onDismiss = args.onDismiss,
                    )
                }

                is MpesaUiState.Error -> {
                    ErrorContent(
                        message = (args.uiState as MpesaUiState.Error).message,
                        onRetry = args.onRetry,
                    )
                }

                else -> {
                    PaymentFormContent(
                        phoneNumber = args.phoneNumber,
                        uiState = args.uiState,
                        onPhoneChange = args.onPhoneChange,
                        onDismiss = args.onDismiss,
                        onPayClick = args.onPayClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun MpesaHeader(
    productName: String,
    amount: Double,
) {
    Text(
        text = "🟢 Lipa Na M-Pesa",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF00A651),
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        text = productName,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = formatAmount(amount),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFFA500),
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun SuccessContent(
    message: String,
    onDismiss: () -> Unit,
) {
    Text(
        text = message,
        color = Color(0xFF00A651),
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onDismiss,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A651)),
    ) {
        Text("Done", color = Color.White)
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
) {
    Text(
        text = "❌ $message",
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onRetry,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
    ) {
        Text("Try Again", color = Color.White)
    }
}

@Composable
private fun PaymentFormContent(
    phoneNumber: String,
    uiState: MpesaUiState,
    onPhoneChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onPayClick: () -> Unit,
) {
    OutlinedTextField(
        value = phoneNumber,
        onValueChange = onPhoneChange,
        label = { Text("M-Pesa Phone Number") },
        placeholder = { Text("07XXXXXXXX") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        enabled = uiState !is MpesaUiState.Loading,
    )

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }

        Button(
            onClick = onPayClick,
            enabled = phoneNumber.isNotBlank() && uiState !is MpesaUiState.Loading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A651)),
        ) {
            if (uiState is MpesaUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = Color.White,
                    strokeWidth = 2.dp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sending...", color = Color.White)
            } else {
                Text("Pay Now", color = Color.White)
            }
        }
    }
}

private fun formatAmount(amount: Double): String {
    return "KES ${String.format(Locale.getDefault(), "%.2f", amount)}"
}
