package com.example.financial_calculator.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financial_calculator.R


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Color de fondo gris claro
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a la Calculadora Financiera",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 25.sp
            ),
            modifier = Modifier
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        // Botones
        Button(
            onClick = { navController.navigate("products") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cálculos de Productos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("employer") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cálculos de Empleador")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("employee") },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Cálculos de Empleado")
        }
    }
}
