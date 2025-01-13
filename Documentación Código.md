# Documentación Código: XP Tracker App

## 1. Estructura Principal de la Aplicación

### MainActivity
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}
```
Esta es la actividad principal de la aplicación. Inicializa la navegación principal mediante `AppNavigation()` utilizando Jetpack Compose.

## 2. Navegación

### AppScene
```kotlin
sealed class AppScene(val route: String) {
    object Lista: AppScene("Lista")
    object Horas: AppScene("Horas")
}
```
Define las rutas de navegación de la aplicación:
- `Lista`: Pantalla principal con la lista de juegos
- `Horas`: Pantalla que muestra el total de horas jugadas

### AppNavigation
```kotlin
@Composable
fun AppNavigation() {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = AppScene.Lista.route) {
        composable(AppScene.Lista.route) {
           Lista(navControlador)
        }
        composable(AppScene.Horas.route) {
            Horas(navControlador)
        }
    }
}
```
Configura la navegación entre pantallas utilizando NavHost de Jetpack Navigation.

## 3. Modelo de Datos

### Juego
```kotlin
data class Juego (
    val titulo: String,
    val descripcion: String,
    val horas: Int
)
```
Clase de datos que representa cada juego con:
- título
- descripción
- horas jugadas

## 4. Gestión de Datos

### Funciones de Persistencia
```kotlin
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
```
Estas funciones manejan el almacenamiento persistente de los juegos:
- `loadJuegos`: Carga la lista de juegos desde SharedPreferences
- `saveJuegos`: Guarda la lista de juegos en SharedPreferences

## 5. Pantallas Principales

### Lista
La pantalla Lista (`@Composable fun Lista`) contiene:

#### Estados
```kotlin
var juegos by remember { mutableStateOf(loadJuegos(sharedPreferences)) }
var titulo by remember { mutableStateOf("") }
var descripcion by remember { mutableStateOf("") }
var horas by remember { mutableStateOf("") }
var showDialog by remember { mutableStateOf(false) }
```
- Gestiona el estado de la lista de juegos y los campos del formulario

#### Drawer de Navegación
```kotlin
ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = { ... }
)
```
- Menú lateral con navegación entre pantallas

#### Lista de Juegos
```kotlin
LazyColumn(modifier = Modifier.padding(16.dp)) {
    items(juegos) { juego ->
        Card( ... )
    }
}
```
- Muestra los juegos en tarjetas desplazables
- Cada tarjeta incluye título, descripción y horas
- Botón de eliminación para cada juego

#### Diálogo de Añadir Juego
```kotlin
AlertDialog(
    onDismissRequest = { showDialog = false },
    // ...
)
```
- Formulario para añadir nuevos juegos
- Validación de campos obligatorios

### Horas
La pantalla Horas (`@Composable fun Horas`) muestra:
- El total de horas jugadas
- Menú de navegación lateral
- Diseño minimalista centrado en el contador de horas

## 6. Estilos y Temas

La aplicación utiliza recursos de color definidos:
- `R.color.fondo`: Color de fondo principal
- `R.color.verdeoscuro`: Color para elementos destacados
- `R.color.verde`: Color secundario
- `R.color.blando`: Color para textos y elementos de contraste

## 7. Características Principales

1. **Gestión de Juegos**
   - Añadir nuevos juegos
   - Eliminar juegos existentes
   - Visualización en forma de lista

2. **Persistencia de Datos**
   - Almacenamiento local usando SharedPreferences
   - Formato de datos serializado usando separadores

3. **Navegación**
   - Menú lateral (drawer)
   - Navegación entre pantallas
   - Gestión de estado de navegación

4. **UI/UX**
   - Diseño Material Design 3
   - Componentes modernos de Jetpack Compose
   - Diseño responsive y adaptativo
