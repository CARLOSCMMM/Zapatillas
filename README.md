# Proyecto Zapatillas

## Introducción

Este proyecto es una aplicación de Android que muestra una lista de zapatillas. La aplicación permite ver, agregar, editar y eliminar zapatillas de la lista.

## Estructura de Clases

### `MainActivity.kt`

Esta es la actividad principal de la aplicación.

*   `onCreate(savedInstanceState: Bundle?)`: Se llama cuando se crea la actividad. Infla el layout, inicializa el `controller` y configura el `RecyclerView`.
*   `iniciarApp()`: Inicializa el `controller` y configura el `RecyclerView`.

### `Controller.kt`

Esta clase actúa como controlador, gestionando la lógica de la aplicación.

*   `inicializarDatos()`: Inicializa la lista de zapatillas obteniéndolas del `DaoZapatilla`.
*   `configurarRecyclerView(binding: ActivityMainBinding)`: Configura el `RecyclerView`, incluyendo el adaptador y el `LayoutManager`. También configura el `ClickListener` para el botón de agregar.
*   `borrarZapatilla(posicion: Int)`: Elimina una zapatilla de la lista y notifica al adaptador sobre el cambio.
*   `editarZapatilla(posicion: Int)`: Muestra un `Toast` indicando que la funcionalidad de edición estará disponible próximamente.
*   `agregarZapatilla()`: Muestra un `Toast` indicando que la funcionalidad de agregar estará disponible próximamente.

### `AdapterZapatilla.kt`

Este es el adaptador para el `RecyclerView` que muestra la lista de zapatillas.

*   `onCreateViewHolder(parent: ViewGroup, viewType: Int)`: Crea un nuevo `ViewHolderZapatilla` inflando el layout del item.
*   `onBindViewHolder(holder: ViewHolderZapatilla, position: Int)`: Vincula los datos de una zapatilla en una posición específica al `ViewHolder`.
*   `getItemCount()`: Devuelve el número total de zapatillas en la lista.

### `ViewHolderZapatilla.kt`

Esta clase representa cada item individual en el `RecyclerView`.

*   `renderize(zapatilla: Zapatilla, position: Int, onDeleteClick: (Int) -> Unit, onEditClick: (Int) -> Unit)`: Renderiza los datos de la zapatilla en la vista, incluyendo el nombre, la marca, el precio y la imagen. También configura los `ClickListeners` para los botones de eliminar y editar.

### `DaoZapatillas.kt`

Esta clase de acceso a datos (DAO) es responsable de obtener los datos de las zapatillas.

*   `obtenerZapatillas()`: Devuelve una lista de zapatillas del `Repositorio`.

### `Repositorio.kt`

Este objeto singleton contiene la lista de zapatillas hardcodeada.

*   `listaZapatillas`: Una lista de objetos `Zapatilla` que se utiliza en toda la aplicación.

### `Zapatilla.kt`

Esta es la clase de modelo de datos para una zapatilla.

*   `nombre: String`: El nombre de la zapatilla.
*   `marca: String`: La marca de la zapatilla.
*   `precio: Double`: El precio de la zapatilla.
*   `imagenUrl: String`: La URL de la imagen de la zapatilla.

### `AndroidManifest.xml`

Este archivo de AndroidManifest contiene la configuración de metadatos de la aplicación.

* `<uses-permission android:name="android.permission.INTERNET" />` es para dar permisos para renderizar las imagenes de las Url.