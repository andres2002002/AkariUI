# AkariUI

AkariUI es una colección modular de componentes, utilidades y patrones estandarizados diseñados para facilitar la creación de interfaces modernas en Jetpack Compose. El objetivo es permitirte desarrollar aplicaciones más coherentes, escalables y reutilizables, manteniendo una experiencia clara tanto para el desarrollador como para el usuario.

---

## 🚀 Objetivos del proyecto

* Proveer **componentes reutilizables** con buenas prácticas.
* Mantener una **arquitectura clara**, adaptable a cualquier proyecto Android.
* Tener una capa UI desacoplada del resto de la app.
* Ofrecer herramientas adicionales como:

    * Estilos y temas personalizados.
    * Utilidades comunes.
    * Extensiones para Compose.
    * Estructuras de navegación.

---

## 📦 Estructura del proyecto

El proyecto sigue una estructura modular clara para facilitar el mantenimiento.

```
uicomponents/
 ├─ buttons/        # Botones con funciones y comportamientos Personalizados
 └─ textFields/     # TextFields con estilos y comportamientos Personalizados

```

Cada módulo está pensado para ser independiente y fácil de probar.

---

## 🧩 Componentes incluidos

Actualmente el proyecto incluye componentes como:

* **TextFields personalizados** con estados, colores y animaciones.
* **Botones avanzados** (primarios, secundarios, iconificados, etc.).
* **TooltipFAB** y variaciones.
* **Contenedores, superficies y tarjetas**.
* **Comportamientos reutilizables**, como manejo de focus, acciones del teclado, animaciones comunes.

Cada componente está diseñado para ser:

* Fácilmente integrable.
* Totalmente personalizable.
* Consistente con Material 3 pero extendido.

---

## 🛠️ Configuración y uso

Si el proyecto se distribuye como módulo local:

### 1. Ejecuta los comandos en AkariUI

```bash
./gradlew :uicomponents:assembleRelease
./gradlew :uicomponents:publishReleasePublicationToMavenLocal
```

### 2. Agrega `mavenLocal` en `settings.gradle` del proyecto a exportar

```kotlin
repositories {
    google()
    mavenCentral()
    mavenLocal()
}
```

### 3. Añádelo como dependencia

```kotlin
dependencies {
    implementation("com.akari:uicomponents:1.0.0")
}
```

### 4. Usa los componentes

```kotlin
AkariTextField(
    value = text,
    onValueChange = { text = it },
    label = "Nombre",
)
```

## 🧪 Pruebas

El proyecto fomenta pruebas unitarias(Soon) y de UI.
Recomendaciones:

* Probar cada componente individual.
* Usar previews interactivas.

---

## 🛤️ Roadmap

* [ ] Más componentes avanzados (SegmentedControls, Date/Time pickers).
* [ ] Sistema de motion y animaciones global.
* [ ] Integración con multiplatform (Compose Multiplatform).
* [ ] Documentación completa por componente.

---

## 🤝 Contribuciones

Para contribuir:

1. Haz un fork del repositorio.
2. Crea una rama descriptiva (`feature/mi-mejora`).
3. Envia un Pull Request detallado.

---

## 📄 Licencia

MIT — Libre para usar, modificar y distribuir. ¡Dame crédito si puedes!

---

## 📬 Contacto

Para dudas, mejoras o ideas:

* Abre un issue.
* Envía comentarios directamente.

AkariUI está en crecimiento continuo (o eso intentare). ¡Gracias por usarlo! 🎉
