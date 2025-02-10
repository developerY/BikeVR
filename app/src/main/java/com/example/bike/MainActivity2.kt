package com.example.bike

// MainActivity.kt
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import com.example.bike.ui.theme.BikeTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Your helper for an immersive, edge-to-edge UI

        setContent {
            BikeTheme {
                // Retrieve the current XR session and spatial capabilities.
                val session = LocalSession.current
                if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
                    // Use the Subspace container for spatial UI.
                    Subspace {
                        MySpatialContent(
                            onRequestHomeSpaceMode = { session?.requestHomeSpaceMode() }
                        )
                    }
                } else {
                    // Fallback 2D content.
                    My2DContent(
                        onRequestFullSpaceMode = { session?.requestFullSpaceMode() }
                    )
                }
            }
        }
    }
}