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
    implementation("com.akari:uicomponents:1.1.31")
}
```

### 4. Use the components

AkariTextField example:
```kotlin
@Composable
fun TextFieldExample() {
    var value by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val firsFocusRequester = remember { FocusRequester() }
    val shapes = MaterialTheme.shapes
    val typography = MaterialTheme.typography
    val config: AkariTextFieldConfig = rememberAkariTextFieldConfig {
        slots {
            label = {
                Text(
                    text = "Label",
                    style = typography.labelLarge
                )
            }
            placeholder = { Text("Placeholder") }
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            }
        }
        style {
            shape = shapes.extraSmall
            textStyle = typography.titleLarge
        }
        behavior {
            autoSelectOnFocus = true
            labelBehavior = AkariLabelBehavior.FLOATING
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            )
        }
    }

    AkariTextField(
        value = value,
        onValueChange = { value = it },
        config = config,
        focusRequester = firsFocusRequester
    )
}
```

AkariCheckBox example:
```kotlin
@Composable
fun CheckBoxExample(){
    var checked by remember { mutableStateOf(false) }
    AkariCheckBox(
        modifier = Modifier.padding(32.dp),
        checked = checked,
        onCheckedChange = { checked = it },
        shape = MaterialTheme.shapes.small,
        iconSelected = {
            Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.background)
        }
    )
}
```

AkariTooltipButton example:
```kotlin
@Composable
fun TooltipButtonExample(){
    val shapes = MaterialTheme.shapes
    val colors = MaterialTheme.colorScheme
    AkariTooltipButton(
        variant = AkariButtonVariant.Filled,
        onClick = {},
        buttonConfig = AkariButtonConfig {
            shape = shapes.extraSmall
            border = BorderStroke(width = 3.dp, color = colors.tertiaryContainer)
        },
        tooltipContent = {
            Text(text = "Tooltip Example")
        }
    ){
        Text(text = "Hello World")
    }
}
```

AkariReorderableLazyColumn example:
```kotlin
@Composable
fun DragAndDropExample() {
    val items = remember {
        mutableStateListOf("Akari", "Compose", "Hilt", "Room", "Navigation", "Python")
    }

    val reorderState = rememberAkariReorderableLazyState<String>{ from, to ->
        items.apply { add(to, removeAt(from)) }
    }

    AkariReorderableLazyColumn(
        items = items,
        state = reorderState,
        reorderingEnabled = true,  // false para deshabilitar
        dragActivation = DragActivation.LongPress,
        key = { it }
    ) { item, isDragging ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDragging) Color.Gray.copy(alpha = 0.3f)
                else Color.DarkGray
            )
        ) {
            Icon(
                modifier = Modifier.akariDragHandle(),
                imageVector = Icons.Default.DragHandle, contentDescription = null)
            Text(
                text = item,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}
```

AkariReorderableColumn example:
```kotlin
@Composable
fun DragDropColumnExample() {
    val items = remember {
        mutableStateListOf("Akari", "Compose", "Hilt", "Room", "Navigation", "Python")
    }

    val reorderState = rememberAkariReorderableColumnState<String> { from, to ->
        items.apply { add(to, removeAt(from)) }
    }

    AkariReorderableColumn(
        items = items,
        state = reorderState
    ) { item, isDragging ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDragging) Color.Gray.copy(alpha = 0.25f)
                else Color.DarkGray
            )
        ) {
            Icon(
                modifier = Modifier.akariDragHandle(),
                imageVector = Icons.Default.DragHandle, contentDescription = null
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = item,
                color = Color.White
            )
        }
    }
}
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
