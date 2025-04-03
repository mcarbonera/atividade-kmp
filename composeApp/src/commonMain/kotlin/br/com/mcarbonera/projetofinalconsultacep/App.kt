package br.com.mcarbonera.projetofinalconsultacep

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.mcarbonera.projetofinalconsultacep.ui.buscacep.BuscaCepScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "buscacep",
            modifier = modifier
        ) {
            composable(route = "buscacep") {
                BuscaCepScreen()
            }
        }
    }
}