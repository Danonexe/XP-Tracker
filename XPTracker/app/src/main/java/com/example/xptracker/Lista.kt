import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.xptracker.R
import kotlinx.coroutines.launch
import com.example.xptracker.model.Juego
import com.example.xptracker.navigation.AppScene

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lista(navControlador: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MisJuegosPrefs", Context.MODE_PRIVATE)

    // Estado para la lista de juegos
    var juegos by remember { mutableStateOf(loadJuegos(sharedPreferences)) }

    // Estados para el formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var horas by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                Modifier
                    .width(230.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
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
                        .clickable {},
                    color = colorResource(R.color.blando)
                )
                Text(
                    "Tiempo Perdido",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {navControlador.navigate(route = AppScene.Horas.route)},
                    color = colorResource(R.color.blando)
                )
            }
        },
        content = {
            Scaffold(
                containerColor = colorResource(R.color.fondo),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { showDialog = true },
                        containerColor = colorResource(R.color.fondo)
                    ) {
                        Text("+", fontSize = 24.sp, color = colorResource(R.color.verdeoscuro))
                    }
                },
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
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxHeight()
                        .background(colorResource(R.color.verde))
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(juegos) { juego ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(colorResource(R.color.verdeoscuro))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = juego.titulo,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = colorResource(R.color.blando)
                                        )
                                        Text(
                                            text = juego.descripcion,
                                            fontSize = 14.sp,
                                            color = colorResource(R.color.blando)
                                        )
                                        Text(
                                            text = "Horas jugadas: ${juego.horas}",
                                            fontSize = 12.sp,
                                            color = colorResource(R.color.blando)
                                        )
                                    }
                                    IconButton(onClick = {
                                        juegos = juegos.filter { it != juego }
                                        saveJuegos(sharedPreferences, juegos)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint =  colorResource(R.color.blando)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            if (titulo.isNotEmpty() && descripcion.isNotEmpty() && horas.isNotEmpty()) {
                                val nuevoJuego = Juego(titulo, descripcion, horas.toInt())
                                juegos = juegos + nuevoJuego
                                saveJuegos(sharedPreferences, juegos)
                                titulo = ""
                                descripcion = ""
                                horas = ""
                                showDialog = false
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.verdeoscuro)
                        )) {
                            Text("Añadir")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }, colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.verdeoscuro)
                        )) {
                            Text("Cancelar")
                        }
                    },
                    title = { Text("Añadir Juego", color = colorResource(R.color.verdeoscuro)) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
                            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                            OutlinedTextField(value = horas, onValueChange = { horas = it }, label = { Text("Horas Jugadas") })
                        }
                    }
                )
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

fun saveJuegos(sharedPreferences: SharedPreferences, juegos: List<Juego>) {
    val editor = sharedPreferences.edit()
    val juegosString = juegos.joinToString(";") { "${it.titulo},${it.descripcion},${it.horas}" }
    editor.putString("juegos", juegosString)
    editor.apply()
}