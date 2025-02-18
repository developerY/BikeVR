package com.example.bike

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bike.ui.theme.BikeTheme

/**
 * A SetupPage composable that instructs the user to prepare their device for the XR experience.
 *
 * This page informs the user about the necessary XR capabilities and permissions, and includes
 * a button to proceed into the XR app.
 */
@Composable
fun SetupPage(
    onContinue: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to the Android XR Bike App Setup",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This experience requires an XR-enabled device and the necessary permissions. " +
                        "Please ensure that your device supports XR, and grant any required permissions when prompted.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onContinue) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, name = "Setup Page Preview")
@Composable
fun SetupPagePreview() {
    BikeTheme {
        SetupPage(onContinue = { /* Preview action */ })
    }
}
