package com.example.bike

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.Volume
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.offset
import androidx.xr.compose.subspace.layout.scale
import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Vector3
import androidx.xr.scenecore.SpatialCapabilities
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch


@Composable
fun ObjectInVolume(show3DObject: Boolean) {
    if (!show3DObject) return

    // A local state for our spin angle (in degrees).
    var angle by remember { mutableFloatStateOf(360f) }

    // Continuously increment 'angle' to spin the bike.
    LaunchedEffect(Unit) {
        while (true) {
            angle += 1f  // Adjust increment to control spin speed.
            // ~60 fps
            kotlinx.coroutines.delay(16L)
        }
    }

    // Build a rotation quaternion from 'angle' around the Y-axis
    val rotation = remember(angle) {
        axisAngleQuaternion(angle, Vector3(0f, 1f, 0f))
    }

    // Pose with translation 0.5m forward, using our dynamic rotation
    val pose = Pose(
        translation = Vector3(0f, 0f, 0.3f),
        rotation = rotation
    )

    // The rest of your existing code
    Text("Model")

    val xrCoreSession = checkNotNull(LocalSession.current)
    val scope = rememberCoroutineScope()

    Subspace {
        Volume(
            modifier = SubspaceModifier
                .offset(0.dp, 0.dp, 0.dp)
                .scale(1f)
        ) {
            scope.launch {
                // load the 3D model
                val gltfModel = xrCoreSession.createGltfResourceAsync("hover_bike/scene.gltf").await()
                if (xrCoreSession.getSpatialCapabilities().hasCapability(SpatialCapabilities.SPATIAL_CAPABILITY_3D_CONTENT)) {
                    val gltfModelEntity = xrCoreSession.createGltfEntity(gltfModel)

                    // Apply the dynamic pose (which updates every frame).
                    gltfModelEntity.setPose(pose)
                    // Scale down if needed
                    gltfModelEntity.setScale(0.001f)

                    // Start an animation if you like
                    gltfModelEntity.startAnimation(loop = true, animationName = "Hovering")
                }
            }
        }
    }
}
