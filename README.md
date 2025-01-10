# XP Tracker

**XP Tracker** es una aplicación de Android diseñada para llevar un registro de tus videojuegos y las horas que les has dedicado. La aplicación se centra en la **persistencia de datos**, utilizando `SharedPreferences` para almacenar de forma local la información de los juegos que el usuario introduce, incluso después de cerrar la aplicación.

## Características

1. **Persistencia de Datos**:  
   Los datos de los juegos se guardan de forma permanente en el dispositivo mediante `SharedPreferences`. Esto permite que el historial de juegos y las horas totales dedicadas se mantengan disponibles incluso después de reiniciar la aplicación.

2. **Historial de Juegos**:  
   Una de las pantallas de la aplicación presenta una lista en la que puedes añadir tus juegos. Para cada juego puedes incluir:  
   - **Título**: El nombre del juego.  
   - **Descripción**: Una breve descripción del juego.  
   - **Horas Jugadas**: El número de horas que has dedicado al juego.

   Los juegos añadidos se mostrarán en esta lista, que funciona como un historial para organizar y revisar los juegos jugados.

3. **Horas Totales**:  
   La otra pantalla de la aplicación muestra la **suma total de las horas jugadas en todos los videojuegos registrados**. Este valor se actualiza automáticamente al añadir o eliminar juegos de la lista.

4. **Interfaz Simple y Elegante**:  
   La aplicación está construida con **Jetpack Compose**, lo que permite un diseño moderno, limpio y fácil de usar.

## Tecnologías Usadas

- **Kotlin**: Lenguaje de programación principal.
- **Jetpack Compose**: Framework para construir la interfaz de usuario.
- **SharedPreferences**: Mecanismo de almacenamiento clave-valor para persistir los datos en el dispositivo.

## Cómo Funciona

1. **Añadir Juegos al Historial**:  
   En la pantalla principal, presiona el botón flotante para añadir un nuevo juego al historial. Completa los campos de título, descripción y horas jugadas. Al confirmar, el juego se añadirá a la lista y se guardará automáticamente.

2. **Visualizar las Horas Totales**:  
   Cambia a la segunda pantalla para visualizar la suma total de horas jugadas. Este valor refleja todos los juegos que hayas registrado en la aplicación.

3. **Eliminar Juegos**:  
   Si ya no deseas conservar un juego en el historial, puedes eliminarlo directamente desde la lista.

## Objetivo Principal

El propósito de esta aplicación es proporcionar una herramienta sencilla para organizar y visualizar el tiempo invertido en videojuegos, priorizando el uso de **persistencia de datos locales** para garantizar que la información no se pierda. Es ideal para quienes desean llevar un control personal y eficiente de sus sesiones de juego.
