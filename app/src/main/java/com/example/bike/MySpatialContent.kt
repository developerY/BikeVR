package com.example.bike

// MySpatialContent.kt
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@SuppressLint("RestrictedApi")
@Composable
fun MySpatialContent(onRequestHomeSpaceMode: () -> Unit) {
    SpatialPanel(
        modifier = SubspaceModifier
            .size(DpVolumeSize(width = 1280.dp, height = 800.dp, depth = 7.dp))
            .resizable()
            .movable()
    ) {
        // Display the 3D model over the entire spatial panel.
        HoverBikeModelViewer(modifier = Modifier.fillMaxSize())

        // Overlay a mode-switch button using the Orbiter.
        Orbiter(
            position = OrbiterEdge.Top,
            offset = EdgeOffset.inner(offset = 20.dp),
            alignment = androidx.compose.ui.Alignment.End,
            //shape = SpatialRoundedCornerShape(CornerSize(28.dp))
        ) {
            HomeSpaceModeIconButton(
                onClick = onRequestHomeSpaceMode,
                modifier = Modifier
                    .fillMaxSize() // or use a fixed size, e.g. Modifier.size(56.dp)
            )
        }
    }
}

@Composable
fun CornerSize(x0: Dp) {
    TODO("Not yet implemented")
}
