package com.example.bike

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HudOverlay(
    speed: Double,
    distance: Double,
    cadence: Double,
    heartRate: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Place the HUD in the top-left corner
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            Text("Speed: ${String.format("%.1f", speed)} km/h", style = MaterialTheme.typography.bodyMedium)
            Text("Distance: ${String.format("%.1f", distance)} km", style = MaterialTheme.typography.bodyMedium)
            Text("Cadence: ${cadence.toInt()} rpm", style = MaterialTheme.typography.bodyMedium)
            Text("Heart Rate: $heartRate bpm", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun HudOverlaySample() {
    // Simulate metrics; in your real app, these would come from sensors or calculations.
    var speed by remember { mutableStateOf(25.3) }
    var distance by remember { mutableStateOf(12.8) }
    var cadence by remember { mutableStateOf(90.0) }
    var heartRate by remember { mutableStateOf(135) }

    // Update metrics every second to simulate live data.
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            speed += 0.1
            distance += 0.05
            cadence = (cadence + 0.2) % 120
            heartRate = (heartRate + 1) % 200
        }
    }

    HudOverlay(speed = speed, distance = distance, cadence = cadence, heartRate = heartRate)
}

@Preview(showBackground = true)
@Composable
fun HudOverlayPreview() {
    MaterialTheme {
        HudOverlaySample()
    }
}
