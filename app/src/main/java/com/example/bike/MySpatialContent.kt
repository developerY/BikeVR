package com.example.bike

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.EdgeOffset
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.size
import androidx.xr.compose.unit.DpVolumeSize
import androidx.xr.scenecore.Session
import com.example.bike.ui.theme.BikeTheme

@Composable
fun MySpatialContentBike(onRequestHomeSpaceMode: () -> Unit) {
    val activity = LocalActivity.current
    if (LocalSession.current != null && activity is ComponentActivity) {
        val uiIsSpatialized = LocalSpatialCapabilities.current.isSpatialUiEnabled
        val environmentController = remember(activity) {
            val session = Session.create(activity)
            EnvironmentController(session, activity.lifecycleScope)
        }

        SpatialPanel(
            modifier = SubspaceModifier
                .size(DpVolumeSize(width = 1280.dp, height = 800.dp, depth = 7.dp))
                .resizable()
                .movable()
        ) {
            // Render a 3D model (explained below)
            if (uiIsSpatialized) {
                environmentController.loadModelAsset("hover_bike/scene.gltf", LocalSession.current!!)
            }

                // Overlay a mode-switch button
                Orbiter(
                    position = OrbiterEdge.Top,
                    offset = EdgeOffset.inner(offset = 20.dp),
                    alignment = Alignment.End,
                    shape = SpatialRoundedCornerShape(CornerSize(28.dp))
                ) {
                    HomeSpaceModeIconButton(
                        onClick = onRequestHomeSpaceMode,
                    )
                }
            }
        }

        @Composable
        fun My2DContentBike(onRequestFullSpaceMode: () -> Unit) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hello Android XR",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(48.dp)
                    )
                    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
                        FullSpaceModeIconButton(
                            onClick = onRequestFullSpaceMode,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                }
            }
        }
    }

// --- Previews ---

@Preview(showBackground = true)
@Composable
fun My2DContentBikePreview() {
    BikeTheme {
        MySpatialContentBike(onRequestHomeSpaceMode = {})
    }
}

/**
* Note: The spatial preview may not render properly in Android Studio's preview,
* since it relies on runtime XR components. This preview is provided for completeness.
*/
@Preview(showBackground = true)
@Composable
fun MySpatialContentBikePreview() {
    BikeTheme {
        MySpatialContentBike(onRequestHomeSpaceMode = {})
    }
}
