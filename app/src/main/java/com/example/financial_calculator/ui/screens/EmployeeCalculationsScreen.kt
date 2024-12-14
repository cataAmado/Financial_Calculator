package com.example.financial_calculator.ui.screens

import android.icu.text.NumberFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.financial_calculator.viewmodel.EmployeeCalculationsViewModel
import java.util.Locale

@Composable
fun EmployeeScreen(navController: NavController) {
    val viewModel: EmployeeCalculationsViewModel = viewModel()
    val isPopupVisible = remember { mutableStateOf(false) } // Estado para el popup

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cálculos para Empleados",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,

            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Salario mensual
        OutlinedTextField(
            value = viewModel.salarioBase.value,
            onValueChange = { viewModel.updateSalarioBase(it) },
            label = { Text("Salario Mensual") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Bonificaciones
        OutlinedTextField(
            value = viewModel.bonificaciones.value,
            onValueChange = { viewModel.updateBonificaciones(it) },
            label = { Text("Bonificaciones") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para horas extras diurnas
        OutlinedTextField(
            value = viewModel.horasExtrasDiurnas.value,
            onValueChange = { viewModel.updateHorasExtrasDiurnas(it) },
            label = { Text("Horas Extras Diurnas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para horas extras nocturnas
        OutlinedTextField(
            value = viewModel.horasExtrasNocturnas.value,
            onValueChange = { viewModel.updateHorasExtrasNocturnas(it) },
            label = { Text("Horas Extras Nocturnas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para horas extras festivas
        OutlinedTextField(
            value = viewModel.horasExtrasFestivas.value,
            onValueChange = { viewModel.updateHorasExtrasFestivas(it) },
            label = { Text("Horas Extras Festivas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para realizar cálculo
        Button(
            onClick = { viewModel.calcularSalario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular Salario Neto")

        }

        // Mostrar el resultado
        ResultadosEmpleado(viewModel)

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
            HistorialPopup2(
                historial = viewModel.historialCalculos2.value,
                onDismissRequest = { isPopupVisible.value = false }
            )
        }
    }
}

@Composable
fun HistorialPopup2(historial: List<String>, onDismissRequest: () -> Unit) {
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
fun ResultadosEmpleado(viewModel: EmployeeCalculationsViewModel) {
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
        Text("Salario Neto: ${decimalFormat.format(viewModel.salarioNeto.value)}")
        Text("Deducciones Totales: ${decimalFormat.format(viewModel.deduccionesTotales.value)}")
        Text("Valor Total Extras Diurnas: ${decimalFormat.format(viewModel.totalExtrasDiurnas.value)}")
        Text("Valor Total Extras Nocturnas: ${decimalFormat.format(viewModel.totalExtrasNocturnas.value)}")
        Text("Valor Total Extras Festivas: ${decimalFormat.format(viewModel.totalExtrasFestivas.value)}")
    }
}