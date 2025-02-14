package com.example.bike


//import kotlinx.coroutines.guava.await
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge

@Composable
fun BottomEdgeOrbiter() {
    Orbiter(
        alignment = Alignment.CenterHorizontally,
        offset = 100.dp,
        position = OrbiterEdge.Bottom,
    ) {
        Surface(
            Modifier
                .clip(CircleShape)
        ) {
            Row(
                Modifier
                    .width(450.dp)
                    .height(100.dp)
                    .background(Color(0.25f, 0.0f, 0.0f)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Bathyscaphe",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 6.em
                )
            }
        }
    }
}
