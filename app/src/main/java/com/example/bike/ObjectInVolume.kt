package com.example.bike

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.xr.compose.platform.LocalSession
import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Vector3
import androidx.xr.scenecore.SpatialCapabilities
import kotlinx.coroutines.delay
import kotlinx.coroutines.guava.await


@Composable
fun ObjectInVolume(show3DObject: Boolean) {
    if (!show3DObject) return

    val xrCoreSession = checkNotNull(LocalSession.current)

    // Keep track of the spin angle in a remember'd state.
    var angle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        // 1. Load the model asynchronously
        val gltfModel = xrCoreSession.createGltfResourceAsync("hover_bike/scene.gltf").await()

        // 2. Check if 3D content is supported
        if (xrCoreSession.getSpatialCapabilities().hasCapability(SpatialCapabilities.SPATIAL_CAPABILITY_3D_CONTENT)) {
            // 3. Create the glTF entity
            val entity = xrCoreSession.createGltfEntity(gltfModel)

            // Scale the entity down, start an animation if available
            entity.setScale(0.001f)
            entity.startAnimation(loop = true, animationName = "Hovering")

            // 4. Continuously update the rotation in a ~60 FPS loop
            while (true) {
                angle += 1f
                val rotation = axisAngleQuaternion(angle, Vector3(0f, 1f, 0f))
                val pose = Pose(
                    translation = Vector3(0f, 0f, 0.5f),  // 0.5m in front
                    rotation = rotation
                )
                entity.setPose(pose)
                delay(16L) // ~60 FPS
            }
        }
    }
}
