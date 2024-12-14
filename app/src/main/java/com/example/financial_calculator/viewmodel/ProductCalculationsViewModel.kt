package com.example.financial_calculator.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProductCalculationsViewModel : ViewModel() {
    // Campos de entrada
    var basePrice = mutableStateOf("")
    var cost = mutableStateOf("")
    var fixedCosts = mutableStateOf("")
    var variableCostPerUnit = mutableStateOf("")
    var investment = mutableStateOf("")
    var income = mutableStateOf("")

    // Resultados
    var priceWithIVA = mutableStateOf(0.0)
    var profitMargin = mutableStateOf(0.0)
    var breakevenPoint = mutableStateOf(0.0)
    var roi = mutableStateOf(0.0)

    val historialCalculos3 = mutableStateOf<List<String>>(emptyList())

    // Validación de entrada: solo números y un punto decimal
    private fun validateInput(input: String): Boolean {
        return input.matches(Regex("^\\d*\\.?\\d*\$"))
    }

    // Actualizaciones de los campos con validación
    fun updateBasePrice(input: String) {
        if (validateInput(input)) {
            basePrice.value = input
        }
    }

    fun updateCost(input: String) {
        if (validateInput(input)) {
            cost.value = input
        }
    }

    fun updateFixedCosts(input: String) {
        if (validateInput(input)) {
            fixedCosts.value = input
        }
    }

    fun updateVariableCostPerUnit(input: String) {
        if (validateInput(input)) {
            variableCostPerUnit.value = input
        }
    }

    fun updateInvestment(input: String) {
        if (validateInput(input)) {
            investment.value = input
        }
    }

    fun updateIncome(input: String) {
        if (validateInput(input)) {
            income.value = input
        }
    }

    // Funciones para realizar los cálculos
    fun calculateResults() {
        val basePriceValue = basePrice.value.toDoubleOrNull() ?: 0.0
        val costValue = cost.value.toDoubleOrNull() ?: 0.0
        val fixedCostsValue = fixedCosts.value.toDoubleOrNull() ?: 0.0
        val variableCostValue = variableCostPerUnit.value.toDoubleOrNull() ?: 0.0
        val investmentValue = investment.value.toDoubleOrNull() ?: 0.0
        val incomeValue = income.value.toDoubleOrNull() ?: 0.0

        priceWithIVA.value = basePriceValue * 1.19
        profitMargin.value = if (basePriceValue > 0) ((priceWithIVA.value - costValue) / priceWithIVA.value) * 100 else 0.0
        breakevenPoint.value = if (basePriceValue > 0) fixedCostsValue / (basePriceValue - variableCostValue) else 0.0
        roi.value = if (investmentValue > 0) ((incomeValue - investmentValue) / investmentValue) * 100 else 0.0

        // Guardar en el historial
        val resultado = """
    Precio Base: ${basePriceValue}
    Precio con IVA: ${priceWithIVA.value}
    Margen de Ganancia: ${profitMargin.value}
    Punto de Equilibrio: ${breakevenPoint.value}
    ROI: ${roi.value}
""".trimIndent()
        historialCalculos3.value = (listOf(resultado) + historialCalculos3.value).take(4)
    }
}
