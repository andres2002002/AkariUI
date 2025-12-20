# AkariUI

AkariUI is a modular collection of UI components, utilities, and standardized patterns designed to simplify the creation of modern user interfaces with **Jetpack Compose**. Its main goal is to help you build applications that are **consistent, scalable, and reusable**, while keeping a clean and pleasant experience for both developers and end users.

---

## 🚀 Project Goals

* Provide **reusable components** built with best practices in mind.
* Maintain a **clear and flexible architecture** adaptable to any Android project.
* Keep the UI layer **fully decoupled** from the rest of the application.
* Offer additional tools such as:

    * Common utilities.
    * Advanced UI components.

---

## 📦 Project Structure

The project follows a clear modular structure to improve maintainability and scalability:

```
uicomponents/
 ├─ buttons/                 # Buttons with custom behaviors and styles
 ├─ checkbox/                # Custom styled and behavior-driven checkboxes
 ├─ reorderableComponents/   # Reorderable LazyColumns and Columns
 └─ textFields/              # TextFields with advanced styles and behaviors
```

Each module is designed to be **independent**, easy to test, and simple to integrate.

---

## 🧩 Included Components

Currently, AkariUI provides components such as:

* **Custom TextFields** with state handling, colors, animations, and fine-grained design control.
* **Advanced Buttons** with tooltips (icon, text, filled, outlined variants).
* **TooltipFAB** and related variations.
* **Custom Checkbox** components.
* **Reorderable Columns** (both Lazy and standard vertical layouts).

All components are designed to be:

* Easy to integrate.
* Fully customizable.
* Material 3–compatible, while extending its capabilities.

---

## 🛠️ Setup and Usage

If you want to use AkariUI as a local module:

### 1. Build and publish AkariUI

Run the following commands from the AkariUI project root:

```bash
./gradlew :uicomponents:assembleRelease
./gradlew :uicomponents:publishReleasePublicationToMavenLocal
```

### 2. Add `mavenLocal` to your project

In your target project's `settings.gradle`:

```kotlin
repositories {
    google()
    mavenCentral()
    mavenLocal()
}
```

### 3. Add the dependency

```kotlin
dependencies {
    implementation("com.akari:uicomponents:1.0.0")
}
```

### 4. Use the components

```kotlin
AkariTextField(
    value = text,
    onValueChange = { text = it },
    label = "Name",
)
```

---

## 🧪 Testing

The project encourages both **unit tests** (coming soon) and **UI tests**.

Recommendations:

* Test each component in isolation.
* Use interactive previews whenever possible.

---

## 🛤️ Roadmap

* [ ] More advanced components (Segmented Controls, Date/Time Pickers).
* [ ] Complete documentation for each component.

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository.
2. Create a descriptive branch (e.g. `feature/my-improvement`).
3. Submit a detailed Pull Request.

---

## 📄 License

MIT — Free to use, modify, and distribute. Attribution is appreciated.

---

## 📬 Contact

For questions, suggestions, or ideas:

* Open an issue.
* Send feedback directly.

AkariUI is continuously evolving (or at least trying to). Thanks for using it! 🎉
