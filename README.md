# ZenithPortfolio - Android

A native Android cryptocurrency portfolio tracker built with Jetpack Compose, following Clean Architecture principles and the MVI (Model-View-Intent) pattern. Track real-time crypto prices, manage your portfolio, and visualize market trends with interactive charts.

---

## Screenshots

> _Coming soon_

---

## Table of Contents

- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Build & Run](#build--run)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Requirements

Before you begin, make sure you have the following installed on your machine:

| Tool | Minimum Version | Recommended |
|------|----------------|-------------|
| **Android Studio** | Ladybug (2024.2.1) or newer | Latest stable |
| **JDK** | 17 | 17 (bundled with Android Studio) |
| **Gradle** | 8.11.1 (included via wrapper) | Use the wrapper (`./gradlew`) |
| **Android SDK** | API 24 (minSdk) | API 36 (compileSdk / targetSdk) |
| **Kotlin** | 2.1.10 | Bundled with the project |

### Android SDK Components

Through Android Studio's **SDK Manager** (`Settings > Languages & Frameworks > Android SDK`), ensure you have:

- **SDK Platform**: Android API 36
- **Build Tools**: Latest available for API 36
- **SDK Command-line Tools** (optional, for command-line builds)
- **Android Emulator** (if you don't have a physical device)

### No API Keys Required

This project uses the free [CoinGecko API](https://www.coingecko.com/) which does not require an API key for basic usage. You can clone and run immediately.

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ZenithPortfolio-Android.git
cd ZenithPortfolio-Android
```

### 2. Open in Android Studio

1. Open **Android Studio**.
2. Select **File > Open** (or **Open** from the Welcome screen).
3. Navigate to the cloned `ZenithPortfolio-Android` folder and select it.
4. Click **OK**. Android Studio will detect the Gradle project automatically.

### 3. Wait for Gradle Sync

- Android Studio will begin downloading dependencies and syncing the project. This may take several minutes on the first run.
- You will see a progress bar at the bottom of the IDE. Wait until it says **"Gradle sync finished"**.
- If prompted to install missing SDK components, click **Install** and follow the prompts.

---

## Build & Run

### Option A: Using Android Studio (Recommended for Beginners)

1. **Select a device**:
   - **Physical device**: Connect via USB, enable **USB Debugging** in Developer Options on the phone, and it should appear in the device dropdown at the top toolbar.
   - **Emulator**: Click the device dropdown > **Device Manager** > **Create Virtual Device** > Choose a phone (e.g., Pixel 7) > Select system image **API 36** (download if needed) > **Finish**. Then select the emulator from the dropdown.

2. **Run the app**:
   - Click the green **Run** button (triangle icon) in the top toolbar, or press `Shift + F10` (Windows/Linux) / `Ctrl + R` (macOS).
   - Android Studio will compile the project, install the APK, and launch the app.

3. **Wait for the build**:
   - The first build may take 2-5 minutes. Subsequent builds will be faster thanks to Gradle caching.

### Option B: Using the Command Line

```bash
# Make the Gradle wrapper executable (macOS/Linux only)
chmod +x ./gradlew

# Build a debug APK
./gradlew assembleDebug

# Install on a connected device or running emulator
./gradlew installDebug

# Run all checks
./gradlew check
```

The debug APK will be located at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Architecture

This project follows **Clean Architecture** with three distinct layers and the **MVI** (Model-View-Intent) pattern for the presentation layer.

```
┌─────────────────────────────────────────┐
│            Presentation Layer           │
│  (Screens, Components, ViewModels,      │
│   Navigation, Theme)                    │
│  Pattern: MVI (State + Intent + Effect) │
├─────────────────────────────────────────┤
│              Domain Layer               │
│  (Models, Repository Interfaces)        │
│  Pure Kotlin — no Android dependencies  │
├─────────────────────────────────────────┤
│               Data Layer                │
│  (Repository Implementations, Mappers,  │
│   Remote API, Local Database)           │
│  Retrofit + Room                        │
└─────────────────────────────────────────┘
```

### Layer Responsibilities

- **Presentation**: Jetpack Compose UI, ViewModels that expose immutable state, user intents, and one-time side effects. Navigation handled via Navigation Compose.
- **Domain**: Business logic and data contracts. Contains model classes and repository interfaces. Has zero Android framework dependencies.
- **Data**: Implements repository interfaces. Manages remote data sources (Retrofit/OkHttp) and local persistence (Room). Mappers convert between data and domain models.
- **DI**: Hilt modules that wire everything together with dependency injection.

---

## Tech Stack

| Category | Library | Version |
|----------|---------|---------|
| **UI Framework** | Jetpack Compose (Material 3) | BOM 2025.02.00 |
| **Language** | Kotlin | 2.1.10 |
| **Build System** | Gradle (Kotlin DSL) | 8.11.1 |
| **Android Gradle Plugin** | AGP | 8.7.3 |
| **Dependency Injection** | Hilt | 2.53.1 |
| **Annotation Processing** | KSP | 2.1.10-1.0.29 |
| **Networking** | Retrofit | 2.11.0 |
| **HTTP Client** | OkHttp | 4.12.0 |
| **Serialization** | Kotlinx Serialization | 1.7.3 |
| **Local Database** | Room | 2.6.1 |
| **Navigation** | Navigation Compose | 2.8.9 |
| **Image Loading** | Coil 3 | 3.1.0 |
| **Charts** | Vico (Compose M3) | 2.1.2 |
| **Lifecycle** | Lifecycle Runtime/ViewModel Compose | 2.8.7 |
| **Core** | AndroidX Core KTX | 1.15.0 |
| **Splash Screen** | Core Splash Screen | 1.0.1 |
| **Activity** | Activity Compose | 1.10.1 |

---

## Project Structure

```
app/src/main/kotlin/com/example/zenithportfolio/
├── di/                          # Hilt dependency injection modules
├── data/
│   ├── remote/                  # Retrofit API service & DTOs
│   ├── local/                   # Room database, DAOs, entities
│   ├── repository/              # Repository implementations
│   └── mapper/                  # Data <-> Domain model mappers
├── domain/
│   ├── model/                   # Domain models (pure Kotlin)
│   └── repository/              # Repository interfaces
├── presentation/
│   ├── screens/                 # Compose screen composables
│   ├── components/              # Reusable UI components
│   ├── viewmodel/               # ViewModels (MVI state management)
│   ├── navigation/              # Navigation graph & routes
│   └── theme/                   # Material 3 theme, colors, typography
├── MainActivity.kt              # Single Activity entry point
└── ZenithApplication.kt         # Application class (@HiltAndroidApp)
```

---

## Troubleshooting

### Gradle Sync Failed

- **"Could not resolve dependencies"**: Make sure you have an active internet connection. Gradle needs to download dependencies from Maven Central and Google's Maven repository.
- **Proxy/VPN issues**: If you are behind a corporate proxy, configure proxy settings in `gradle.properties`:
  ```properties
  systemProp.http.proxyHost=your.proxy.host
  systemProp.http.proxyPort=8080
  systemProp.https.proxyHost=your.proxy.host
  systemProp.https.proxyPort=8080
  ```
- **Try invalidating caches**: `File > Invalidate Caches > Invalidate and Restart`.

### JDK Version Mismatch

This project requires **JDK 17**. If you see errors like `Unsupported class file major version 61`:

1. Go to `File > Settings > Build, Execution, Deployment > Build Tools > Gradle`.
2. Under **Gradle JDK**, select **JDK 17** (or the embedded JDK bundled with Android Studio).
3. Click **Apply** and re-sync Gradle.

### Emulator Won't Start

- Make sure **hardware acceleration** is enabled:
  - **Windows**: Enable Intel HAXM or Windows Hypervisor Platform in BIOS.
  - **macOS**: Works out of the box on Apple Silicon and Intel Macs with Hypervisor.framework.
  - **Linux**: Enable KVM (`sudo apt install qemu-kvm`).
- Allocate at least **2 GB of RAM** to the emulator in AVD settings.
- If the emulator is very slow, try using a **smaller screen size** (e.g., Pixel 4a instead of Pixel 7 Pro).

### Hilt / KSP Issues

- **"Cannot create an instance of ViewModel"**: Make sure your Activity is annotated with `@AndroidEntryPoint` and your Application class with `@HiltAndroidApp`.
- **KSP errors after adding entities**: Run `Build > Clean Project`, then `Build > Rebuild Project`.
- **Generated code not found**: Ensure the KSP plugin is applied in `build.gradle.kts` and run a Gradle sync.

### Room Schema Issues

- If you get Room schema export errors, the project already has `schemaDirectory` configured in `build.gradle.kts`. Just make sure the `app/schemas/` directory exists.

### Build Takes Too Long

- Increase Gradle memory in `gradle.properties` (already set to 2048m):
  ```properties
  org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
  ```
- Enable Gradle build cache (already enabled by default).
- Use `./gradlew assembleDebug` instead of full `build` to skip tests.

### "SDK location not found"

- If you cloned the project and see this error, create a `local.properties` file in the project root:
  ```properties
  sdk.dir=/path/to/your/Android/sdk
  ```
  Common paths:
  - **macOS**: `/Users/<username>/Library/Android/sdk`
  - **Windows**: `C:\\Users\\<username>\\AppData\\Local\\Android\\Sdk`
  - **Linux**: `/home/<username>/Android/Sdk`

---

## License

This project does not currently include a license. All rights reserved.

---
---

# ZenithPortfolio - Android (Espanol)

Una aplicacion nativa de Android para seguimiento de portafolios de criptomonedas, construida con Jetpack Compose, siguiendo los principios de Arquitectura Limpia (Clean Architecture) y el patron MVI (Model-View-Intent). Rastrea precios de criptomonedas en tiempo real, gestiona tu portafolio y visualiza tendencias del mercado con graficos interactivos.

---

## Capturas de Pantalla

> _Proximamente_

---

## Tabla de Contenidos

- [Requisitos](#requisitos)
- [Primeros Pasos](#primeros-pasos)
- [Compilar y Ejecutar](#compilar-y-ejecutar)
- [Arquitectura](#arquitectura)
- [Stack Tecnologico](#stack-tecnologico)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Solucion de Problemas](#solucion-de-problemas)
- [Licencia](#licencia-1)

---

## Requisitos

Antes de comenzar, asegurate de tener instalado lo siguiente en tu maquina:

| Herramienta | Version Minima | Recomendada |
|-------------|---------------|-------------|
| **Android Studio** | Ladybug (2024.2.1) o superior | Ultima version estable |
| **JDK** | 17 | 17 (incluido con Android Studio) |
| **Gradle** | 8.11.1 (incluido via wrapper) | Usar el wrapper (`./gradlew`) |
| **Android SDK** | API 24 (minSdk) | API 36 (compileSdk / targetSdk) |
| **Kotlin** | 2.1.10 | Incluido en el proyecto |

### Componentes del Android SDK

A traves del **SDK Manager** de Android Studio (`Settings > Languages & Frameworks > Android SDK`), asegurate de tener:

- **SDK Platform**: Android API 36
- **Build Tools**: La ultima version disponible para API 36
- **SDK Command-line Tools** (opcional, para compilar desde la terminal)
- **Android Emulator** (si no tienes un dispositivo fisico)

### No Se Requieren Claves de API

Este proyecto utiliza la API gratuita de [CoinGecko](https://www.coingecko.com/) que no requiere clave de API para uso basico. Puedes clonar y ejecutar inmediatamente.

---

## Primeros Pasos

### 1. Clonar el Repositorio

```bash
git clone https://github.com/your-username/ZenithPortfolio-Android.git
cd ZenithPortfolio-Android
```

### 2. Abrir en Android Studio

1. Abre **Android Studio**.
2. Selecciona **File > Open** (o **Open** desde la pantalla de bienvenida).
3. Navega hasta la carpeta clonada `ZenithPortfolio-Android` y seleccionala.
4. Haz clic en **OK**. Android Studio detectara el proyecto Gradle automaticamente.

### 3. Esperar la Sincronizacion de Gradle

- Android Studio comenzara a descargar dependencias y sincronizar el proyecto. Esto puede tomar varios minutos en la primera ejecucion.
- Veras una barra de progreso en la parte inferior del IDE. Espera hasta que diga **"Gradle sync finished"**.
- Si se te solicita instalar componentes SDK faltantes, haz clic en **Install** y sigue las instrucciones.

---

## Compilar y Ejecutar

### Opcion A: Usando Android Studio (Recomendado para Principiantes)

1. **Seleccionar un dispositivo**:
   - **Dispositivo fisico**: Conectalo por USB, habilita la **Depuracion USB** en las Opciones de Desarrollador del telefono, y deberia aparecer en el menu desplegable de dispositivos en la barra superior.
   - **Emulador**: Haz clic en el menu desplegable de dispositivos > **Device Manager** > **Create Virtual Device** > Elige un telefono (ej. Pixel 7) > Selecciona la imagen del sistema **API 36** (descargala si es necesario) > **Finish**. Luego selecciona el emulador del menu desplegable.

2. **Ejecutar la aplicacion**:
   - Haz clic en el boton verde de **Run** (icono de triangulo) en la barra superior, o presiona `Shift + F10` (Windows/Linux) / `Ctrl + R` (macOS).
   - Android Studio compilara el proyecto, instalara el APK y lanzara la aplicacion.

3. **Esperar la compilacion**:
   - La primera compilacion puede tomar de 2 a 5 minutos. Las compilaciones posteriores seran mas rapidas gracias al cache de Gradle.

### Opcion B: Usando la Linea de Comandos

```bash
# Hacer ejecutable el wrapper de Gradle (solo macOS/Linux)
chmod +x ./gradlew

# Compilar un APK de depuracion
./gradlew assembleDebug

# Instalar en un dispositivo conectado o emulador en ejecucion
./gradlew installDebug

# Ejecutar todas las verificaciones
./gradlew check
```

El APK de depuracion se encontrara en:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Arquitectura

Este proyecto sigue **Clean Architecture** (Arquitectura Limpia) con tres capas diferenciadas y el patron **MVI** (Model-View-Intent) para la capa de presentacion.

```
┌─────────────────────────────────────────┐
│         Capa de Presentacion            │
│  (Pantallas, Componentes, ViewModels,   │
│   Navegacion, Tema)                     │
│  Patron: MVI (State + Intent + Effect)  │
├─────────────────────────────────────────┤
│            Capa de Dominio              │
│  (Modelos, Interfaces de Repositorio)   │
│  Kotlin puro — sin dependencias Android │
├─────────────────────────────────────────┤
│             Capa de Datos               │
│  (Implementaciones de Repositorio,      │
│   Mapeadores, API Remota, BD Local)     │
│  Retrofit + Room                        │
└─────────────────────────────────────────┘
```

### Responsabilidades de Cada Capa

- **Presentacion**: UI con Jetpack Compose, ViewModels que exponen estado inmutable, intenciones del usuario y efectos secundarios unicos. Navegacion manejada con Navigation Compose.
- **Dominio**: Logica de negocio y contratos de datos. Contiene clases de modelo e interfaces de repositorio. No tiene dependencias del framework Android.
- **Datos**: Implementa las interfaces de repositorio. Gestiona fuentes de datos remotas (Retrofit/OkHttp) y persistencia local (Room). Los mapeadores convierten entre modelos de datos y de dominio.
- **DI**: Modulos de Hilt que conectan todo mediante inyeccion de dependencias.

---

## Stack Tecnologico

| Categoria | Libreria | Version |
|-----------|----------|---------|
| **Framework de UI** | Jetpack Compose (Material 3) | BOM 2025.02.00 |
| **Lenguaje** | Kotlin | 2.1.10 |
| **Sistema de Build** | Gradle (Kotlin DSL) | 8.11.1 |
| **Plugin Android Gradle** | AGP | 8.7.3 |
| **Inyeccion de Dependencias** | Hilt | 2.53.1 |
| **Procesamiento de Anotaciones** | KSP | 2.1.10-1.0.29 |
| **Red** | Retrofit | 2.11.0 |
| **Cliente HTTP** | OkHttp | 4.12.0 |
| **Serializacion** | Kotlinx Serialization | 1.7.3 |
| **Base de Datos Local** | Room | 2.6.1 |
| **Navegacion** | Navigation Compose | 2.8.9 |
| **Carga de Imagenes** | Coil 3 | 3.1.0 |
| **Graficos** | Vico (Compose M3) | 2.1.2 |
| **Ciclo de Vida** | Lifecycle Runtime/ViewModel Compose | 2.8.7 |
| **Core** | AndroidX Core KTX | 1.15.0 |
| **Pantalla de Inicio** | Core Splash Screen | 1.0.1 |
| **Actividad** | Activity Compose | 1.10.1 |

---

## Estructura del Proyecto

```
app/src/main/kotlin/com/example/zenithportfolio/
├── di/                          # Modulos de inyeccion de dependencias (Hilt)
├── data/
│   ├── remote/                  # Servicio API Retrofit y DTOs
│   ├── local/                   # Base de datos Room, DAOs, entidades
│   ├── repository/              # Implementaciones de repositorio
│   └── mapper/                  # Mapeadores Datos <-> Dominio
├── domain/
│   ├── model/                   # Modelos de dominio (Kotlin puro)
│   └── repository/              # Interfaces de repositorio
├── presentation/
│   ├── screens/                 # Composables de pantallas
│   ├── components/              # Componentes de UI reutilizables
│   ├── viewmodel/               # ViewModels (gestion de estado MVI)
│   ├── navigation/              # Grafo de navegacion y rutas
│   └── theme/                   # Tema Material 3, colores, tipografia
├── MainActivity.kt              # Punto de entrada (Activity unica)
└── ZenithApplication.kt         # Clase Application (@HiltAndroidApp)
```

---

## Solucion de Problemas

### Fallo en la Sincronizacion de Gradle

- **"Could not resolve dependencies"**: Asegurate de tener una conexion a internet activa. Gradle necesita descargar dependencias de Maven Central y del repositorio Maven de Google.
- **Problemas de proxy/VPN**: Si estas detras de un proxy corporativo, configura los ajustes de proxy en `gradle.properties`:
  ```properties
  systemProp.http.proxyHost=tu.proxy.host
  systemProp.http.proxyPort=8080
  systemProp.https.proxyHost=tu.proxy.host
  systemProp.https.proxyPort=8080
  ```
- **Intenta invalidar caches**: `File > Invalidate Caches > Invalidate and Restart`.

### Incompatibilidad de Version de JDK

Este proyecto requiere **JDK 17**. Si ves errores como `Unsupported class file major version 61`:

1. Ve a `File > Settings > Build, Execution, Deployment > Build Tools > Gradle`.
2. En **Gradle JDK**, selecciona **JDK 17** (o el JDK embebido incluido con Android Studio).
3. Haz clic en **Apply** y re-sincroniza Gradle.

### El Emulador No Inicia

- Asegurate de que la **aceleracion por hardware** este habilitada:
  - **Windows**: Habilita Intel HAXM o Windows Hypervisor Platform en la BIOS.
  - **macOS**: Funciona directamente en Apple Silicon e Intel Macs con Hypervisor.framework.
  - **Linux**: Habilita KVM (`sudo apt install qemu-kvm`).
- Asigna al menos **2 GB de RAM** al emulador en la configuracion del AVD.
- Si el emulador es muy lento, prueba usar un **tamano de pantalla mas pequeno** (ej. Pixel 4a en lugar de Pixel 7 Pro).

### Problemas con Hilt / KSP

- **"Cannot create an instance of ViewModel"**: Asegurate de que tu Activity tenga la anotacion `@AndroidEntryPoint` y tu clase Application tenga `@HiltAndroidApp`.
- **Errores de KSP al agregar entidades**: Ejecuta `Build > Clean Project`, luego `Build > Rebuild Project`.
- **Codigo generado no encontrado**: Verifica que el plugin KSP este aplicado en `build.gradle.kts` y ejecuta una sincronizacion de Gradle.

### Problemas con el Esquema de Room

- Si obtienes errores de exportacion de esquema de Room, el proyecto ya tiene `schemaDirectory` configurado en `build.gradle.kts`. Solo asegurate de que el directorio `app/schemas/` exista.

### La Compilacion Toma Demasiado Tiempo

- Aumenta la memoria de Gradle en `gradle.properties` (ya configurado a 2048m):
  ```properties
  org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
  ```
- Habilita el cache de compilacion de Gradle (ya habilitado por defecto).
- Usa `./gradlew assembleDebug` en lugar de `build` completo para omitir los tests.

### "SDK location not found"

- Si clonaste el proyecto y ves este error, crea un archivo `local.properties` en la raiz del proyecto:
  ```properties
  sdk.dir=/ruta/a/tu/Android/sdk
  ```
  Rutas comunes:
  - **macOS**: `/Users/<usuario>/Library/Android/sdk`
  - **Windows**: `C:\\Users\\<usuario>\\AppData\\Local\\Android\\Sdk`
  - **Linux**: `/home/<usuario>/Android/Sdk`

---

## Licencia

Este proyecto actualmente no incluye una licencia. Todos los derechos reservados.
