package com.example.xptracker

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.xptracker.model.Juego
import com.example.xptracker.navigation.AppScene
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Horas(navControlador: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MisJuegosPrefs", Context.MODE_PRIVATE)

    // Estado para la lista de juegos
    val juegos = loadJuegos(sharedPreferences)

    // Calcular el total de horas
    val totalHoras = juegos.sumOf { it.horas }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                Modifier
                    .width(230.dp)
                    .fillMaxHeight()
                    .background(color = colorResource(R.color.fondo))
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    "Menú",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.blando)
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "Experiencia",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {navControlador.navigate(route = AppScene.Lista.route)},
                    color = colorResource(R.color.blando)
                )
                Text(
                    "Tiempo Perdido",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {},
                    color = colorResource(R.color.blando)
                )
            }
        },
        content = {
            Scaffold(
                containerColor = colorResource(R.color.fondo),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = colorResource(R.color.fondo),
                            titleContentColor = colorResource(R.color.blando)
                        ),
                        title = {
                            Text("XP Tracker", color = colorResource(R.color.verdeoscuro))
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Abrir menú",
                                    tint = colorResource(R.color.blando)
                                )
                            }
                        }
                    )
                },
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(colorResource(R.color.verde)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Horas perdidas: $totalHoras",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = colorResource(R.color.blando)
                    )
                }
            }
        }
    )
}

fun loadJuegos(sharedPreferences: SharedPreferences): List<Juego> {
    val juegosString = sharedPreferences.getString("juegos", "") ?: ""
    return if (juegosString.isEmpty()) emptyList() else juegosString.split(";").map {
        val partes = it.split(",")
        Juego(partes[0], partes[1], partes[2].toInt())
    }
}
