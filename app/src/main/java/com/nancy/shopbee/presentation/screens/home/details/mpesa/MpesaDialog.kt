package com.nancy.shopbee.presentation.screens.home.details.mpesa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MpesaPaymentDialog(
    productName: String,
    amount: Double,
    onDismiss: () -> Unit,
    viewModel: MpesaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {
        viewModel.resetState()
        onDismiss()
    }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // M-Pesa Header
                Text(
                    text = "🟢 Lipa Na M-Pesa",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00A651)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = productName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Amount display
                Text(
                    text = "KES ${String.format("%.2f", amount)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFA500)
                )

                Spacer(modifier = Modifier.height(20.dp))

                when (uiState) {
                    is MpesaUiState.Success -> {
                        Text(
                            text = (uiState as MpesaUiState.Success).message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF00A651),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.resetState()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00A651)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Done", color = Color.White)
                        }
                    }

                    is MpesaUiState.Error -> {
                        Text(
                            text = "❌ ${(uiState as MpesaUiState.Error).message}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.resetState() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Try Again", color = Color.White)
                        }
                    }

                    else -> {
                        // Phone number input
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text("M-Pesa Phone Number") },
                            placeholder = { Text("07XXXXXXXX") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiState !is MpesaUiState.Loading
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                viewModel.resetState()
                                onDismiss()
                            }) {
                                Text("Cancel")
                            }

                            Button(
                                onClick = {
                                    viewModel.initiatePay(
                                        phoneNumber = phoneNumber,
                                        amount = amount.toInt().toString(),
                                        accountRef = "ShopBee",
                                        description = "Payment for $productName"
                                    )
                                },
                                enabled = phoneNumber.isNotBlank() && uiState !is MpesaUiState.Loading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF00A651)
                                )
                            ) {
                                if (uiState is MpesaUiState.Loading) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(18.dp),
                                                color = Color.White,
                                                strokeWidth = 2.dp
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Sending...", color = Color.White)
                                        }
                                    }
                                } else {
                                    Text("Pay Now", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

