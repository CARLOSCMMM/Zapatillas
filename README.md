# Proyecto Zapatillas

## Introduccion

Aplicacion Android para gestionar una lista de zapatillas. Permite ver el listado, agregar, editar y eliminar, ver detalle, y autenticar usuarios con Firebase (login, registro y recuperacion de contrasena).

## Como esta construido

Arquitectura por capas con MVVM y un estilo tipo Clean:

1. Presentacion: Activities, Fragments, Adapter y ViewModel.
2. Dominio: modelo `Zapatilla`, interfaz `Repositorio` y casos de uso.
3. Datos: `RepositorioImpl` con una lista en memoria.
4. DI: Hilt para inyectar dependencias.

Flujo principal:

1. La UI llama al `ZapatillaViewModel`.
2. El ViewModel usa un caso de uso.
3. El caso de uso llama al repositorio.
4. El repositorio devuelve/actualiza la lista.
5. El ViewModel publica la lista con LiveData para que la UI se actualice.

Tecnologias clave:

1. Hilt (inyeccion de dependencias).
2. LiveData y ViewModel.
3. Navigation Component para el flujo lista -> detalle en Fragment.
4. Glide para cargar imagenes.
5. Firebase Auth para login, registro y recuperacion.

Nota: las zapatillas se guardan solo en memoria (no en Firebase ni base de datos).

## Estructura de clases y metodos

### `app/src/main/java/com/example/zapatillas/ZapatillasApp.kt`

Clase de aplicacion para iniciar Hilt.

1. `ZapatillasApp : Application`: activa Hilt con `@HiltAndroidApp`.

### `app/src/main/java/com/example/zapatillas/di/DiModule.kt`

Modulo de inyeccion de dependencias.

1. `provideRepositorio(repo: RepositorioImpl): Repositorio`: enlaza la interfaz con la implementacion.

### `app/src/main/java/com/example/zapatillas/domain/model/Zapatillas.kt`

Modelo de datos.

1. `data class Zapatilla`: id, nombre, marca, precio e imagenUrl. Es `Parcelable` para pasarla entre pantallas.

### `app/src/main/java/com/example/zapatillas/domain/repositorio/Repositorio.kt`

Contrato del repositorio.

1. `getZapatillas()`: devuelve la lista.
2. `addZapatilla(zapatilla)`: agrega una nueva.
3. `updateZapatilla(old, new)`: actualiza una existente.
4. `deleteZapatilla(zapatilla)`: elimina una existente.

### `app/src/main/java/com/example/zapatillas/data/repositorio/RepositorioImpl.kt`

Implementacion en memoria del repositorio.

1. `listaZapatillas`: lista inicial con varias zapatillas.
2. `getZapatillas()`: devuelve una copia de la lista.
3. `addZapatilla(zapatilla)`: genera un id nuevo y agrega.
4. `updateZapatilla(old, new)`: busca por id y reemplaza.
5. `deleteZapatilla(zapatilla)`: elimina por id.

### `app/src/main/java/com/example/zapatillas/domain/usecase/GetZapatillasUseCase.kt`

Caso de uso para leer la lista.

1. `invoke()`: devuelve `repositorio.getZapatillas()`.

### `app/src/main/java/com/example/zapatillas/domain/usecase/AddZapatillaUseCase.kt`

Caso de uso para agregar.

1. `invoke(zapatilla)`: llama a `repositorio.addZapatilla(zapatilla)`.

### `app/src/main/java/com/example/zapatillas/domain/usecase/UpdateZapatillaUseCase.kt`

Caso de uso para editar.

1. `invoke(old, new)`: llama a `repositorio.updateZapatilla(old, new)`.

### `app/src/main/java/com/example/zapatillas/domain/usecase/DeleteZapatillaUseCase.kt`

Caso de uso para eliminar.

1. `invoke(zapatilla)`: llama a `repositorio.deleteZapatilla(zapatilla)`.

### `app/src/main/java/com/example/zapatillas/presentacion/viewmodel/ZapatillaViewModel.kt`

ViewModel que coordina la UI y los casos de uso.

1. `init`: carga la lista al iniciar.
2. `loadZapatillas()`: lee la lista con `GetZapatillasUseCase`.
3. `addZapatilla(zapatilla)`: agrega y recarga.
4. `updateZapatilla(old, new)`: edita y recarga.
5. `deleteZapatilla(zapatilla)`: elimina y recarga.
6. `getZapatillaByIndex(index)`: devuelve el item por posicion si existe.
7. `refresh()`: vuelve a cargar la lista.

### `app/src/main/java/com/example/zapatillas/presentacion/adapter/AdapterZapatilla.kt`

Adaptador del RecyclerView.

1. `updateList(newList)`: actualiza la lista y refresca.
2. `onCreateViewHolder(...)`: infla `item_zapatilla`.
3. `onBindViewHolder(...)`: pinta los datos del item.
4. `getItemCount()`: devuelve el tamano de la lista.

### `app/src/main/java/com/example/zapatillas/presentacion/adapter/ViewHolderZapatilla.kt`

ViewHolder de cada item.

1. `renderize(...)`: muestra nombre, marca, precio y carga imagen con Glide. Configura clicks de borrar, editar y detalle.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/main/MainActivity.kt`

Pantalla principal con Drawer y lista.

1. `onCreate(...)`: configura toolbar, drawer, menu lateral, recycler y listeners.
2. `setupRecyclerView()`: crea el adapter y asigna layout manager.
3. `deleteZapatilla(position)`: borra usando el ViewModel.
4. `editZapatilla(position)`: abre `AddEditZapatillaActivity` con la zapatilla.
5. `showDetails(position)`: abre `ZapatillaDetailActivity`.
6. `agregarZapatilla()`: abre `AddEditZapatillaActivity` vacia.
7. `onCreateOptionsMenu(...)`: infla menu de opciones.
8. `onOptionsItemSelected(...)`: gestiona logout y abrir el drawer.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/main/ZapatillaListFragment.kt`

Fragment con la lista (usa Navigation).

1. `onCreateView(...)`: infla la vista con ViewBinding.
2. `onViewCreated(...)`: prepara RecyclerView y observa LiveData.
3. `setupRecyclerView()`: crea adapter y layout manager.
4. `deleteZapatilla(position)`: borra y muestra un Toast.
5. `editZapatilla(position)`: abre `AddEditZapatillaActivity`.
6. `showDetails(position)`: navega al detalle con `zapatillaId` (posicion).
7. `agregarZapatilla()`: abre `AddEditZapatillaActivity`.
8. `onDestroyView()`: limpia el binding.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/detail/ZapatillaDetailFragment.kt`

Detalle con Navigation.

1. `onCreateView(...)`: infla la vista con ViewBinding.
2. `onViewCreated(...)`: toma `zapatillaId`, busca en el ViewModel y pinta el detalle.
3. `onDestroyView()`: limpia el binding.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/detail/ZapatillaDetailActivity.kt`

Detalle en Activity (flujo alternativo desde MainActivity).

1. `onCreate(...)`: recibe una `Zapatilla` por intent y muestra el detalle.
2. `buttonVolver`: cierra la pantalla.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/addEdit/AddEditZapatillaActivity.kt`

Pantalla para agregar o editar.

1. `onCreate(...)`: carga campos, detecta modo edicion y rellena datos.
2. `btnGuardar`: crea la zapatilla nueva o editada, llama al caso de uso correspondiente y vuelve con `RESULT_OK`.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/login/LoginActivity.kt`

Login con Firebase Auth.

1. `onCreate(...)`: inicializa ViewBinding y listeners.
2. `init()`: obtiene instancia de Firebase Auth.
3. `start()`: configura botones de login, registro y recuperar contrasena.
4. `recoverPassword(email, onResult)`: envia correo de recuperacion.
5. `startLogin(user, pass, onResult)`: inicia sesion y valida email verificado.

### `app/src/main/java/com/example/zapatillas/presentacion/ui/login/RegisterActivity.kt`

Registro con Firebase Auth.

1. `onCreate(...)`: inicializa ViewBinding y listeners.
2. `init()`: obtiene instancia de Firebase Auth.
3. `start()`: valida campos y lanza registro.
4. `startActivityLogin()`: vuelve al login.
5. `registerUser(email, pass, onResult)`: crea el usuario, envia verificacion y cierra sesion.

### `app/src/main/AndroidManifest.xml`

Manifiesto principal.

1. Permiso de internet para cargar imagenes.
2. Declara la aplicacion `ZapatillasApp`.
3. Declara actividades y la pantalla inicial `LoginActivity`.

### `app/src/main/res/navigation/nav_graph.xml`

Navegacion entre fragments.

1. `ZapatillaListFragment` es el inicio.
2. Accion hacia `ZapatillaDetailFragment` con argumento `zapatillaId`.
