package com.example.bike

import androidx.xr.runtime.math.Quaternion
import androidx.xr.runtime.math.Vector3
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun axisAngleQuaternion(angleDegrees: Float, axis: Vector3): Quaternion {
    val rad = Math.toRadians(angleDegrees.toDouble())
    val sinHalf = sin(rad / 2)
    val cosHalf = cos(rad / 2)

    // Normalize the axis
    val length = sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z)
    val nx = axis.x / length
    val ny = axis.y / length
    val nz = axis.z / length

    return Quaternion(
        x = nx.toFloat() * sinHalf.toFloat(),
        y = ny.toFloat() * sinHalf.toFloat(),
        z = nz.toFloat() * sinHalf.toFloat(),
        w = cosHalf.toFloat()
    )
}
