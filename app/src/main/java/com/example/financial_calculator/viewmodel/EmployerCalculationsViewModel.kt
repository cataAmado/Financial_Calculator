package com.example.financial_calculator.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EmployerCalculationsViewModel : ViewModel() {
    val salarioBaseText = mutableStateOf("") // Estado del texto del salario base
    val salarioBase = mutableStateOf(0.0) // Estado numérico para cálculos
    val aportesParafiscales = mutableStateOf(0.0)
    val seguridadSocial = mutableStateOf(0.0)
    val prestacionesSociales = mutableStateOf(0.0)
    val costoTotalNomina = mutableStateOf(0.0)

    // Historial de los últimos 5 cálculos
    val historialCalculos1 = mutableStateOf<List<String>>(emptyList())

    fun updateSalarioBase(input: String) {
        if (input.matches(Regex("^\\d*\\.?\\d*\$"))) {
            salarioBaseText.value = input
            salarioBase.value = input.toDoubleOrNull() ?: 0.0
        }
    }

    fun calcularCostos() {
        val base = salarioBase.value

        // Fórmulas
        aportesParafiscales.value = base * 0.09
        seguridadSocial.value = base * 0.205
        prestacionesSociales.value = base * 0.2183
          costoTotalNomina.value =
            base + aportesParafiscales.value + seguridadSocial.value + prestacionesSociales.value

        // Guardar en el historial
        val resultado = """
    Salario Base: $base
    Aportes: ${aportesParafiscales.value}
    Seguridad: ${seguridadSocial.value}
    Prestaciones: ${prestacionesSociales.value}
    Total: ${costoTotalNomina.value}
""".trimIndent()
        historialCalculos1.value = (listOf(resultado) + historialCalculos1.value).take(4)
    }
}
