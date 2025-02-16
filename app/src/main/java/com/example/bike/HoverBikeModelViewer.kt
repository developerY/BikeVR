package com.example.bike



import android.util.Log
import androidx.concurrent.futures.await
import androidx.xr.scenecore.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class EnvironmentController(private val xrSession: Session, private val coroutineScope: CoroutineScope) {
    private val assetCache: HashMap<String, Any> = HashMap()



    fun loadModelAsset(modelName: String, xrSession: Session) {

        coroutineScope.launch {
            //load the asset if it hasn't been loaded previously
            if (!assetCache.containsKey(modelName)) {
                try {
                    val gltfModel =
                        xrSession.createGltfResourceAsync(modelName).await()

                    assetCache[modelName] = gltfModel

                } catch (e: Exception) {
                    Log.e(
                        "Hello Android XR",
                        "Failed to load model for $modelName: $e"
                    )
                }
            }
        }
    }
}
