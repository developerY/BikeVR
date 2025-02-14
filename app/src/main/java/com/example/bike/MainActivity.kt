package com.example.bike


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.EdgeOffset
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.size
import androidx.xr.compose.unit.DpVolumeSize
import com.example.bike.ui.theme.BikeTheme


class MainActivityNew : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Your helper for an immersive, edge-to-edge UI

        setContent {
            BikeTheme {
                // Background with gradient.
                Box(modifier = Modifier.fillMaxSize()) {
                    BrushBackground()

                    // Retrieve the current XR session.
                    val session = LocalSession.current

                    // Choose spatial UI if available, otherwise fallback to 2D.
                    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
                        Subspace {
                            MySpatialContent(
                                onRequestHomeSpaceMode = { session?.requestHomeSpaceMode() }
                            )
                        }
                    } else {
                        My2DContent(
                            onRequestFullSpaceMode = { session?.requestFullSpaceMode() }
                        )
                    }
                }
            }
        }
    }
}

// Helper: Enable immersive, edge-to-edge UI (stub or implementation)
fun enableEdgeToEdge() {
    // Implementation to remove system bars and allow content behind them.
}



@Composable
fun BrushBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    )
}




@SuppressLint("RestrictedApi")
@Composable
fun MySpatialContent(onRequestHomeSpaceMode: () -> Unit) {
    SpatialPanel(
        modifier = SubspaceModifier
            .size(DpVolumeSize(width = 1280.dp, height = 800.dp, depth = 7.dp))
            .resizable()
            .movable()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Main content area.
                MainContent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(48.dp)
                )
                // Flashy bike info panel with extra metrics and pulsing animation.
                FlashyBikeInfoPanel(
                    speed = 25.3,
                    distance = 12.8,
                    cadence = 90.0,
                    heartRate = 135,
                    altitude = 150.5,
                    calories = 320,
                    power = 250
                )
                // Overlay a mode switch button via Orbiter.
                Orbiter(
                    position = OrbiterEdge.Top,
                    offset = EdgeOffset.inner(offset = 20.dp),
                    alignment = Alignment.End,
                    shape = SpatialRoundedCornerShape(CornerSize(28.dp))
                ) {
                    HomeSpaceModeIconButton(
                        onClick = onRequestHomeSpaceMode,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}



@SuppressLint("RestrictedApi")
@Composable
fun My2DContent(onRequestFullSpaceMode: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MainContent(modifier = Modifier.padding(48.dp))
            FlashyBikeInfoPanel(
                speed = 25.3,
                distance = 12.8,
                cadence = 90.0,
                heartRate = 135,
                altitude = 150.5,
                calories = 320,
                power = 250
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



@Composable
fun MainContent(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.hello_android_xr),
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground
    )
}



@Composable
fun FlashyBikeInfoPanel(
    speed: Double,
    distance: Double,
    cadence: Double,
    heartRate: Int,
    altitude: Double,
    calories: Int,
    power: Int
) {
    // Create a pulsing animation.
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Create a dynamic gradient background.
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(gradientBrush)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Bike Info",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(12.dp))
            // First row of metrics.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BikeMetric(name = "Speed", value = "${String.format("%.1f", speed)} km/h")
                BikeMetric(name = "Distance", value = "${String.format("%.1f", distance)} km")
                BikeMetric(name = "Cadence", value = "${cadence.toInt()} rpm")
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Second row of metrics.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BikeMetric(name = "Heart Rate", value = "$heartRate bpm")
                BikeMetric(name = "Altitude", value = "${String.format("%.1f", altitude)} m")
                BikeMetric(name = "Calories", value = "$calories cal")
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Single centered metric.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                BikeMetric(name = "Power", value = "$power W")
            }
        }
    }
}



@Composable
fun BikeMetric(name: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



@Composable
fun FullSpaceModeIconButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(CircleShape)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_full_space_mode_switch),
            contentDescription = stringResource(R.string.switch_to_full_space_mode),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}



@Composable
fun HomeSpaceModeIconButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
            .clip(CircleShape)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home_space_mode_switch),
            contentDescription = stringResource(R.string.switch_to_home_space_mode),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}


@Preview(showBackground = true)
@Composable
fun My2dContentPreview() {
    BikeTheme {
        My2DContent(onRequestFullSpaceMode = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FullSpaceModeButtonPreview() {
    BikeTheme {
        FullSpaceModeIconButton(onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeSpaceModeButtonPreview() {
    BikeTheme {
        HomeSpaceModeIconButton(onClick = {})
    }
}
