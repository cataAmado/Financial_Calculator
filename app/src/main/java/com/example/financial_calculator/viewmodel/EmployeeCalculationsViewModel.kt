package com.example.financial_calculator.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EmployeeCalculationsViewModel : ViewModel() {
    // Campos de entrada
    var salarioBase = mutableStateOf("")
    var bonificaciones = mutableStateOf("")
    var horasExtrasDiurnas = mutableStateOf("")
    var horasExtrasNocturnas = mutableStateOf("")
    var horasExtrasFestivas = mutableStateOf("")

    // Resultados
    var salarioNeto = mutableStateOf(0.0)
    var deduccionesTotales = mutableStateOf(0.0)
    var totalExtrasDiurnas = mutableStateOf(0.0)
    var totalExtrasNocturnas = mutableStateOf(0.0)
    var totalExtrasFestivas = mutableStateOf(0.0)

    val historialCalculos2 = mutableStateOf<List<String>>(emptyList())

    // Validación de entrada: solo números y un punto decimal
    private fun validateInput(input: String): Boolean {
        return input.matches(Regex("^\\d*\\.?\\d*\$"))
    }

    // Funciones de actualización con validación
    fun updateSalarioBase(input: String) {
        if (validateInput(input)) {
            salarioBase.value = input
        }
    }

    fun updateBonificaciones(input: String) {
        if (validateInput(input)) {
            bonificaciones.value = input
        }
    }

    fun updateHorasExtrasDiurnas(input: String) {
        if (validateInput(input)) {
            horasExtrasDiurnas.value = input
        }
    }

    fun updateHorasExtrasNocturnas(input: String) {
        if (validateInput(input)) {
            horasExtrasNocturnas.value = input
        }
    }

    fun updateHorasExtrasFestivas(input: String) {
        if (validateInput(input)) {
            horasExtrasFestivas.value = input
        }
    }

    // Función de cálculo
    fun calcularSalario() {
        val salarioBaseValue = salarioBase.value.toDoubleOrNull() ?: 0.0
        val bonificacionesValue = bonificaciones.value.toDoubleOrNull() ?: 0.0
        val horasExtrasDiurnasValue = horasExtrasDiurnas.value.toDoubleOrNull() ?: 0.0
        val horasExtrasNocturnasValue = horasExtrasNocturnas.value.toDoubleOrNull() ?: 0.0
        val horasExtrasFestivasValue = horasExtrasFestivas.value.toDoubleOrNull() ?: 0.0

        // Lógica de cálculo (puedes personalizarla)
        salarioNeto.value = salarioBaseValue + bonificacionesValue
        deduccionesTotales.value = salarioBaseValue * 0.1 // Ejemplo de deducción
        totalExtrasDiurnas.value = horasExtrasDiurnasValue * 1.5
        totalExtrasNocturnas.value = horasExtrasNocturnasValue * 2.0
        totalExtrasFestivas.value = horasExtrasFestivasValue * 2.5

        // Guardar en el historial
        val resultado = """
    Salario Neto: ${salarioNeto.value}
    Deducciones: ${deduccionesTotales.value}
    Valor Horas Extras Diurnas: ${totalExtrasDiurnas.value}
    Valor Horas Extras Nocturnas: ${totalExtrasNocturnas.value}
    Valor Horas Extras Fertivas: ${totalExtrasFestivas.value}
""".trimIndent()
        historialCalculos2.value = (listOf(resultado) + historialCalculos2.value).take(4)
    }
}
