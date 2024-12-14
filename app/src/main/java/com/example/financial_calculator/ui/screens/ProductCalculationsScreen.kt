package com.example.financial_calculator.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financial_calculator.viewmodel.ProductCalculationsViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Composable
fun ProductosScreen(navController: NavController) {
    val viewModel: ProductCalculationsViewModel = viewModel()
    val isPopupVisible = remember { mutableStateOf(false) } // Estado para el popup

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculos de Productos",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada
        OutlinedTextField(
            value = viewModel.basePrice.value,
            onValueChange = { viewModel.updateBasePrice(it)},
            label = { Text("Precio Base") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = viewModel.cost.value,
            onValueChange = { viewModel.updateCost(it) },
            label = { Text("Costo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        OutlinedTextField(
            value = viewModel.fixedCosts.value,
            onValueChange = { viewModel.updateFixedCosts(it)},
            label = { Text("Costos Fijos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = viewModel.variableCostPerUnit.value,
            onValueChange = { viewModel.updateVariableCostPerUnit(it) },
            label = { Text("Costo Variable Unitario") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = viewModel.investment.value,
            onValueChange = { viewModel.updateInvestment(it)},
            label = { Text("Inversión") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = viewModel.income.value,
            onValueChange = { viewModel.updateIncome(it) },
            label = { Text("Ingresos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para ejecutar los cálculos
        Button(
            onClick = { viewModel.calculateResults() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Producto")
        }

        // Mostrar los resultados
        ProductCalculationResults(viewModel)

        Button(
            onClick = { isPopupVisible.value = true }, // Mostrar el popup
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver Historial")
        }

        // Botón para volver al menú principal
        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al Menú Principal")
        }

        // Mostrar el popup si es necesario
        if (isPopupVisible.value) {
            HistorialPopup1(
                historial = viewModel.historialCalculos3.value,
                onDismissRequest = { isPopupVisible.value = false }
            )
        }
    }
}

@Composable
fun HistorialPopup3(historial: List<String>, onDismissRequest: () -> Unit) {
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
fun ProductCalculationResults(viewModel: ProductCalculationsViewModel) {
    // Configurar el formato personalizado
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

        Text("Precio con IVA: ${decimalFormat.format( viewModel.priceWithIVA.value)}")
        Text("Margen de Ganancia: ${decimalFormat.format(viewModel.profitMargin.value)} %")
        Text("Punto de Equilibrio: ${decimalFormat.format(viewModel.breakevenPoint.value)}")
        Text("ROI: $${decimalFormat.format(viewModel.roi.value)} %")
    }
}