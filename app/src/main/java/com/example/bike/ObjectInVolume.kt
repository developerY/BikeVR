package com.example.bike

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.xr.scenecore.GltfModelEntity
import androidx.xr.scenecore.SpatialCapabilities
import kotlinx.coroutines.delay
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch


@Composable
fun ObjectInVolume(show3DObject: Boolean) {
    if (!show3DObject) return

    Text("Model")
    val xrCoreSession = checkNotNull(LocalSession.current)
    val scope = rememberCoroutineScope()

    // State for storing the created entity
    val gltfModelEntityState = remember { mutableStateOf<GltfModelEntity?>(null) }

    // Spin angle in degrees
    var angle by remember { mutableStateOf(0f) }

    // Continuously update the entity's pose in a LaunchedEffect
    LaunchedEffect(gltfModelEntityState.value) {
        val entity = gltfModelEntityState.value ?: return@LaunchedEffect
        while (true) {
            angle += 1f
            val rotation = axisAngleQuaternion(angle, Vector3(0f, 1f, 0f))
            val newPose = Pose(
                translation = Vector3(0f, 0f, 0.5f),
                rotation = rotation
            )
            Log.d("XRApp", "Angle = $angle -> setting pose: $newPose")
            entity.setPose(newPose)
            delay(16L)
        }
    }

    // The Subspace volume
    Subspace {
        Volume(
            modifier = SubspaceModifier
                .offset(0.dp, 0.dp, 0.dp)
                .scale(1f),
        ) {
            scope.launch {
                // Load the 3D model
                val gltfModel = xrCoreSession.createGltfResourceAsync("hover_bike/scene.gltf").await()
                if (
                    xrCoreSession.getSpatialCapabilities().hasCapability(
                        SpatialCapabilities.SPATIAL_CAPABILITY_3D_CONTENT
                    )
                ) {
                    val gltfModelEntity = xrCoreSession.createGltfEntity(gltfModel)
                    // Store the entity in our state so the LaunchedEffect can access it
                    gltfModelEntityState.value = gltfModelEntity

                    // Initial pose & scale
                    gltfModelEntity.setPose(Pose())
                    gltfModelEntity.setScale(0.001f)

                    // Start an animation if available
                    gltfModelEntity.startAnimation(
                        loop = true,
                        animationName = "Hovering",
                    )
                }
            }
        }
    }
}
