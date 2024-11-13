# 📱 *Proyecto App MobiStore - App Sprint Módulo 6*

## 📝 *Descripción*

Esta aplicación de Android muestra una lista de productos con detalles adicionales, diseñada como parte de un sprint de aprendizaje en el Bootcamp. Permite visualizar productos con sus características y precios, enviar consultas sobre ellos por correo electrónico y navegar de manera intuitiva.

## 🛠️ *Características*

📋 *Lista de Productos:* Muestra una lista de productos disponibles, cargada desde una API y almacenada en una base de datos local.
🔍 *Detalle del Producto:* Cada producto muestra información detallada, incluyendo nombre, descripción, precio, imagen, etc.
📧 *Consulta por Correo:* Desde la pantalla de detalles, se puede enviar un correo de consulta sobre el producto seleccionado.
🌐 *Navegación Intuitiva:* Barra de navegación inferior para moverse fácilmente entre las secciones.

## 📦 *Estructura del Proyecto*

*Arquitectura MVVM (Model-View-ViewModel)*

El proyecto sigue el patrón de arquitectura MVVM para separar las responsabilidades y facilitar el mantenimiento del código.

- Model: Contiene las clases de datos como Product y ProductDetail.
- View: Compuestas en Jetpack Compose, representan las pantallas de la aplicación.
- ViewModel: Gestiona los datos y la lógica de negocio, conectándose con el repositorio.
- Repository: Gestiona la obtención de datos desde la API o base de datos local.

## 📂 Estructura del Código

```plaintext
└── src/main
    ├── java/cl/bootcamp/sprintmodulo6
    │   ├── view        # Pantallas en Jetpack Compose
    │   ├── viewmodel   # ProductViewModel y la lógica de datos
    │   ├── util        # Constants
    │   ├── iu.theme    # Color, Theme, Type
    │   ├── room        # DB y DAO
    │   ├── model       # Clases de datos del producto
    │   ├── repository  # Repositorio de productos para manejar la API y la DB
    │   ├── navigation  # Navegación entre pantallas
    │   ├── di          # AppModule
    │   ├── data        # ApiService, Entity, Retrofit
    │   └── components  # Componentes reutilizables, como la barra de navegación
    └── res
        ├── drawable    # Recursos gráficos
        ├── layout      # Archivos de diseño XML (si se usan)
        └── values      # Colores, temas, strings, etc.
  ```
## ⚙️ *Configuración y Dependencias*

Este proyecto usa varias librerías de Jetpack y otras herramientas. Asegúrate de tener las siguientes dependencias en tu archivo build.gradle:

- Jetpack Compose para la interfaz de usuario
- Dagger Hilt para la inyección de dependencias
- Room para la base de datos local
- Retrofit para manejar la API
- Coil para la carga de imágenes

Ejemplo de dependencias en build.gradle:
```plaintext
dependencies {
    implementation "androidx.compose.ui:ui:<version>"
    implementation "androidx.compose.material3:material3:<version>"
    implementation "com.google.dagger:hilt-android:<version>"
    kapt "com.google.dagger:hilt-android-compiler:<version>"
    implementation "androidx.room:room-runtime:<version>"
    kapt "androidx.room:room-compiler:<version>"
    implementation "com.squareup.retrofit2:retrofit:<version>"
    implementation "io.coil-kt:coil-compose:<version>"
}
```
## 🚀 *Funcionalidades*

1.*Lista de Productos*

Muestra los productos obtenidos desde una API y permite seleccionar un producto para ver sus detalles.

2. *Detalle del Producto*

Muestra la información detallada del producto con imagen, descripción y precio, alineada correctamente en la vista.

3. *Enviar Consulta por Correo*

Desde el botón de consulta en la pantalla de detalles, se abre la app de correo con un mensaje predefinido sobre el producto.

## 🖼️ *Pantallas*
1.- Pantalla de Lista de Productos	

2.- Pantalla de Detalles de Producto

## 💡 *Mejoras Futuras*

🌐 Filtrado y Búsqueda de Productos

📊 Soporte para gráficos de tendencias

🌍 Traducciones para más idiomas

## 👥 *Contribuciones*

¡Las contribuciones son bienvenidas! Si tienes sugerencias, puedes abrir un pull request o una issue.

Haz un fork del proyecto.
Crea una nueva rama: git checkout -b feature/nueva-funcionalidad.
Realiza tus cambios y haz commit: git commit -am 'Agregar nueva funcionalidad'.
Haz push a la rama: git push origin feature/nueva-funcionalidad.
Envía un Pull Request.

## 📂 Configuración del Proyecto
Clona el repositorio:

git clone https://github.com/Danny3431/SprintModulo6.git 

Configura tu API key en Constants.kt. Ejecuta el proyecto en Android Studio.

## 🛠️ *Tecnologías Usadas*

- *Kotlin* y *Jetpack Compose* para la interfaz de usuario
- *Dagger Hilt* para la inyección de dependencias
- *Room* para la base de datos local
- *Retrofit* para hacer peticiones HTTP
- *Coil* para la carga de imágenes

  
## ✍️ Autora

Proyecto desarrollado como parte del Bootcamp Desarrollo de Aplicaciones Móviles Android Trainee v2.0 en Adalid.

¡Espero que disfrutes de la aplicación! 📲

