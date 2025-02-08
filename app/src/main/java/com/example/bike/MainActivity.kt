package com.example.bike

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalHasXrSpatialFeature
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.EdgeOffset
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import com.example.bike.ui.theme.BikeTheme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.xr.compose.subspace.layout.size
import androidx.xr.compose.unit.DpVolumeSize


class MainActivity : ComponentActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Your helper for an immersive UI

        setContent {
            BikeTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    BrushBackground()
                    val session = LocalSession.current
                    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
                        Subspace {
                            MySpatialContent(onRequestHomeSpaceMode = { session?.requestHomeSpaceMode() })
                        }
                    } else {
                        My2DContent(onRequestFullSpaceMode = { session?.requestFullSpaceMode() })
                    }
                }
            }
        }
    }
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Main content at the center
                MainContent(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(48.dp)
                )
                // Bike info panel at the bottom
                BikeInfoPanel(
                    speed = 25.3,
                    distance = 12.8,
                    cadence = 90.0,
                    heartRate = 135
                )
                // Orbiter for mode switching, if needed
                Orbiter(
                    position = OrbiterEdge.Top,
                    offset = EdgeOffset.inner(offset = 20.dp),
                    alignment = Alignment.End,
                    shape = SpatialRoundedCornerShape(CornerSize(28.dp))
                ) {
                    HomeSpaceModeIconButton(
                        onClick = onRequestHomeSpaceMode,
                        modifier = Modifier.size(56.dp)
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
            BikeInfoPanel(
                speed = 25.3,
                distance = 12.8,
                cadence = 90.0,
                heartRate = 135
            )
            if (LocalHasXrSpatialFeature.current) {
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
fun BikeInfoPanel(
    speed: Double,
    distance: Double,
    cadence: Double,
    heartRate: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Bike Info",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BikeMetric(name = "Speed", value = "${String.format("%.1f", speed)} km/h")
                BikeMetric(name = "Distance", value = "${String.format("%.1f", distance)} km")
                BikeMetric(name = "Cadence", value = "${cadence.toInt()} rpm")
                BikeMetric(name = "Heart Rate", value = "$heartRate bpm")
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

@PreviewLightDark
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

@PreviewLightDark
@Composable
fun HomeSpaceModeButtonPreview() {
    BikeTheme {
        HomeSpaceModeIconButton(onClick = {})
    }
}
