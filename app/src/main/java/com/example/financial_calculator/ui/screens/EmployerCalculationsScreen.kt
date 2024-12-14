package com.example.financial_calculator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financial_calculator.viewmodel.EmployerCalculationsViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Composable
fun EmployerScreen(navController: NavController) {
    val viewModel: EmployerCalculationsViewModel = viewModel()
    val isPopupVisible = remember { mutableStateOf(false) } // Estado para el popup

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculos para Empleadores",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.salarioBaseText.value,
            onValueChange = { viewModel.updateSalarioBase(it) },
            label = { Text("Salario Base") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.calcularCostos() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular Costos")
        }

        ResultadosEmpleadores(viewModel)

        Button(
            onClick = { isPopupVisible.value = true }, // Mostrar el popup
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver Historial")
        }

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al Menú Principal")
        }
    }

    // Mostrar el popup si es necesario
    if (isPopupVisible.value) {
        HistorialPopup1(
            historial = viewModel.historialCalculos1.value,
            onDismissRequest = { isPopupVisible.value = false }
        )
    }
}

@Composable
fun HistorialPopup1(historial: List<String>, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("Cerrar")
            }
        },
        title = { Text("Historial de Cálculos") },
        text = {
            Column(modifier = Modifier.padding(1.dp)) {
                if (historial.isEmpty()) {
                    Text("No hay cálculos recientes.")
                } else {
                    historial.forEachIndexed { index, item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 1.dp)
                                .then(if (index % 2 == 0) Modifier else Modifier)
                        ) {
                            // Estilo para diferenciar cálculos
                            Text(
                                "Cálculo ${index + 1}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(item, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Divider(color = MaterialTheme.colorScheme.secondary, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ResultadosEmpleadores(viewModel: EmployerCalculationsViewModel) {
    val decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ','
        decimalSeparator = '.'
    }
    val decimalFormat = DecimalFormat("#,##0.00", decimalFormatSymbols)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Aportes parafiscales: ${decimalFormat.format(viewModel.aportesParafiscales.value)}")
        Text("Seguridad social: ${decimalFormat.format(viewModel.seguridadSocial.value)}")
        Text("Prestaciones sociales: ${decimalFormat.format(viewModel.prestacionesSociales.value)}")
        Text("Costo total de nómina: ${decimalFormat.format(viewModel.costoTotalNomina.value)}")
    }
}