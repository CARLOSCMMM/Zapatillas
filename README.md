# Proyecto Zapatillas

## Introducción

Este proyecto es una aplicación de Android que muestra una lista de zapatillas. La aplicación permite ver, agregar, editar y eliminar zapatillas de la lista.

## Estructura de Clases

### `MainActivity.kt`

Esta es la actividad principal de la aplicación.

*   `onCreate(savedInstanceState: Bundle?)`: Se llama cuando se crea la actividad. Infla el layout, inicializa el `controller` y configura el `RecyclerView`.
*   `iniciarApp()`: Inicializa el `controller` pasándole la propia instancia de la actividad para que el controlador pueda gestionar los resultados de otras actividades y, a continuación, configura el `RecyclerView`.
*   `actualizarRecyclerView()`: Llama al método `actualizarRecyclerView` del `controller` para refrescar la lista.

### `Controller.kt`

Esta clase actúa como controlador, gestionando la lógica de la aplicación y la comunicación entre la vista y el modelo.

*   `configurarRecyclerView(binding: ActivityMainBinding)`: Configura el `RecyclerView`, incluyendo el adaptador (que obtiene los datos directamente del `Repositorio`) y el `LayoutManager`. También configura el `ClickListener` para el botón de agregar.
*   `borrarZapatilla(posicion: Int)`: Elimina una zapatilla del `Repositorio` y notifica al adaptador sobre el cambio para que la vista se actualice.
*   `editarZapatilla(posicion: Int)`: Crea un `Intent` para abrir `AddEditZapatillaActivity` en modo edición, pasando los datos de la zapatilla a editar. Lanza la actividad esperando un resultado.
*   `agregarZapatilla()`: Crea un `Intent` para abrir `AddEditZapatillaActivity` en modo de creación. Lanza la actividad esperando un resultado.
*   `addEditLauncher`: Un `ActivityResultLauncher` que se encarga de lanzar `AddEditZapatillaActivity`. Cuando esta actividad termina y devuelve un `RESULT_OK`, se llama al método `actualizarRecyclerView()` para refrescar la lista de zapatillas.
*   `actualizarRecyclerView()`: Notifica al adaptador (`notifyDataSetChanged()`) que los datos han cambiado para que el `RecyclerView` se vuelva a dibujar con la información actualizada del `Repositorio`.

### `AddEditZapatillaActivity.kt`

Esta actividad permite al usuario agregar una nueva zapatilla o editar una existente.

*   `onCreate(savedInstanceState: Bundle?)`: Al crear la actividad, inicializa las vistas. Comprueba si el `Intent` contiene una posición de zapatilla. Si la contiene, activa el "modo edición" y carga los datos de la zapatilla en los campos de texto. De lo contrario, se mantiene en "modo agregar".
*   El botón de "Guardar" lee los datos de los `EditText`, crea un objeto `Zapatilla`, y lo añade o actualiza en el `Repositorio` según si se está en modo agregar o editar.
*   Tras guardar los cambios, llama a `setResult(Activity.RESULT_OK)` para informar a `MainActivity` de que la operación ha sido exitosa. Finalmente, llama a `finish()` para cerrar la actividad y volver a la pantalla principal.

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

Este objeto singleton contiene la lista de zapatillas.

*   `listaZapatillas`: Una `MutableList` de objetos `Zapatilla` que se utiliza en toda la aplicación.
*   `añadirZapatilla(zapatilla: Zapatilla)`: Añade una nueva zapatilla a `listaZapatillas`.
*   `editarZapatilla(posicion: Int, zapatilla: Zapatilla)`: Actualiza una zapatilla existente en `listaZapatillas` en la posición indicada.

### `Zapatilla.kt`

Esta es la clase de modelo de datos para una zapatilla.

*   `nombre: String`: El nombre de la zapatilla.
*   `marca: String`: La marca de la zapatilla.
*   `precio: Double`: El precio de la zapatilla.
*   `imagenUrl: String`: La URL de la imagen de la zapatilla.

### `AndroidManifest.xml`

Este archivo de AndroidManifest contiene la configuración de metadatos de la aplicación.

* `<uses-permission android:name="android.permission.INTERNET" />` es para dar permisos para renderizar las imagenes de las Url.

### `ZapatillaListFragment.kt`

Este fragmento muestra la lista de zapatillas en un `RecyclerView`.

*   `onCreateView()`: Infla el layout del fragmento.
*   `onViewCreated()`: Configura el `RecyclerView` una vez que la vista ha sido creada.
*   `setupRecyclerView()`: Inicializa el `AdapterZapatilla` con la lista de zapatillas del `Repositorio` y lo asigna al `RecyclerView`. También configura los click listeners para eliminar, editar y ver los detalles de una zapatilla.
*   `deleteZapatilla()`: Elimina una zapatilla de la lista y notifica al adaptador para que actualice la vista.
*   `editZapatilla()`: (Función de edición no implementada) Muestra un `Toast` para indicar que la zapatilla seleccionada se va a editar.
*   `showDetails()`: Navega al fragmento de detalles de la zapatilla (`ZapatillaDetailFragment`) cuando se hace clic en un elemento de la lista.
*   `agregarZapatilla()`: (Función de agregar no implementada)
*   `onDestroyView()`: Limpia la referencia al `binding` cuando la vista del fragmento se destruye.

### `ZapatillaDetailActivity.kt`

Esta actividad muestra los detalles de una zapatilla.

*   `onCreate()`: Al crear la actividad, se obtiene la posición de la zapatilla del `Intent` y se muestran sus detalles (nombre, marca, precio e imagen).
*   `buttonVolver`: Se ha añadido un listener al botón "Volver" para que, al pulsarlo, se cierre la actividad y se vuelva a la pantalla anterior.

### `LoginActivity.kt`

Esta actividad gestiona el inicio de sesión del usuario.

*   `onCreate()`: Configura los listeners de los botones de la pantalla de inicio de sesión.
*   `buttonLogin`: Comprueba si el nombre de usuario y la contraseña introducidos son correctos. Si es así, inicia la `MainActivity`. Si no, muestra un mensaje de error.
*   `buttonRegister`: Muestra un `Toast` indicando que la función de registro no está implementada.
*   `buttonForgotPassword`: Muestra un `Toast` indicando que la función de recuperación de contraseña no está implementada.
