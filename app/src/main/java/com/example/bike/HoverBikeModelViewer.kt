package com.example.bike

// HoverBikeModelViewer.kt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.xr.compose.platform.LocalSession

@Composable
fun HoverBikeModelViewer(modifier: Modifier = Modifier) {
    // Get the current XR session.
    val session = LocalSession.current

    // Load the glTF resource asynchronously using the XR session API.
    val gltfResource by produceState<GltfResource?>(initialValue = null, key1 = session) {
        value = session?.createGltfResourceAsync("hover_bike/scene.gltf")
    }

    // Once loaded, render the model using the XrModel composable.
    if (gltfResource != null) {
        XrModel(
            modelResource = gltfResource!!,
            modifier = modifier
        )
    }
}