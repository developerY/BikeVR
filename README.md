# Android XR Bike App

An immersive Android XR app built using the [Jetpack XR SDK](https://developer.android.com/develop/xr/jetpack-xr-sdk). This sample app demonstrates how to create an edge-to-edge XR experience with both spatial (3D) and 2D fallback modes. It includes features such as:

- A gradient background for an immersive feel.
- Adaptive UI that switches between spatial and 2D layouts.
- A 3D model viewer that loads a glTF model asynchronously using the XR session.
- A flashy bike info panel that displays various biking metrics with pulsing animation.
- Mode switch controls to toggle between XR and full-space (or home space) modes.

## Features

- **XR Session Integration:** Uses `LocalSession` from the Jetpack XR SDK to manage XR functionality.
- **Spatial UI:** Renders immersive 3D content in a `SpatialPanel` with interactive elements.
- **2D Fallback:** Provides a simple 2D layout for devices that do not support spatial XR.
- **3D Model Loading:** Asynchronously loads a glTF model (`hover_bike/scene.gltf`) using `createGltfResourceAsync`.
- **Dynamic Bike Info Panel:** Displays metrics such as speed, distance, cadence, heart rate, altitude, calories, and power with animations and gradient effects.
- **Material3 Theming:** Utilizes Material3 components for modern, consistent design.

## Getting Started

### Prerequisites

- **Android Studio Flamingo or later.**
- **Android device or emulator** that supports XR features.
- **Minimum API Level:** 24 (Android 7.0) or higher.
- **Gradle:** Ensure you have an updated Gradle version that supports version catalogs.

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/android-xr-bike-app.git
   cd android-xr-bike-app
   ```

2. **Open the project in Android Studio.**

3. **Set Up Dependencies:**

   This project uses a version catalog (`libs.versions.toml`) to manage dependencies. Ensure your `libs.versions.toml` includes:

   ```toml
   [versions]
   xr = "1.0.0"  # Update to the latest version as needed

   [libraries]
   jetpack-xr-compose = { group = "androidx.xr", name = "jetpack-xr-compose", version.ref = "xr" }
   jetpack-xr-runtime = { group = "androidx.xr", name = "runtime", version.ref = "xr" }
   jetpack-xr-scenecore = { group = "androidx.xr", name = "scenecore", version.ref = "xr" }
   jetpack-xr-model = { group = "androidx.xr", name = "jetpack-xr-model", version.ref = "xr" }
   ```

4. **Sync your project** in Android Studio to download all required dependencies.

### Running the App

1. **Build the project** using Android Studio.
2. **Deploy the app** to a compatible device or emulator.
3. **Experience the XR features**:
    - If your device supports spatial UI, you’ll see a 3D scene displaying the hover bike model along with the animated bike info panel.
    - On devices without XR support, a fallback 2D layout will be presented.

## Project Structure

- **MainActivity.kt:**  
  Sets up the immersive XR experience, applies the gradient background, and chooses between spatial and 2D layouts based on device capabilities.

- **BrushBackground.kt:**  
  Provides a full-screen vertical gradient background.

- **MySpatialContent.kt & My2DContent.kt:**  
  Define the spatial and fallback 2D UI layouts respectively.
    - `MySpatialContent` uses `SpatialPanel` and overlays the 3D model viewer and XR mode-switch controls.
    - `My2DContent` offers a simple 2D interface for devices without spatial support.

- **HoverBikeModelViewer.kt:**  
  Loads the hover bike glTF model asynchronously via the XR session’s `createGltfResourceAsync` API and renders it using `XrModel`.

- **FlashyBikeInfoPanel.kt & BikeMetric.kt:**  
  Display biking metrics with animations and gradient backgrounds.

- **Icon Button Components:**  
  `HomeSpaceModeIconButton` and `FullSpaceModeIconButton` provide interactive controls using Material3 components.

- **Theme Files:**  
  `BikeTheme.kt` contains the Material3 theme configuration for a consistent look and feel.

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to check [issues page](https://github.com/yourusername/android-xr-bike-app/issues) if you want to contribute.

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/YourFeature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin feature/YourFeature`
5. Open a Pull Request.

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.

## Acknowledgements

- [Jetpack XR SDK Documentation](https://developer.android.com/develop/xr/jetpack-xr-sdk)
- [Material3 Design](https://m3.material.io/)
- Special thanks to the Android XR and Filament communities for their ongoing support and contributions.

---

Happy coding, and welcome to the future of immersive Android experiences!
