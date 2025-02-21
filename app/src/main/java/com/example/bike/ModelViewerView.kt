package com.example.bike

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.android.filament.Engine
import com.google.android.filament.EntityManager
import com.google.android.filament.Renderer
import com.google.android.filament.Scene
import com.google.android.filament.SwapChain
import com.google.android.filament.Viewport
import com.google.android.filament.gltfio.AssetLoader
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.gltfio.ResourceLoader
import com.google.android.filament.gltfio.UbershaderLoader
import java.nio.ByteBuffer

class ModelViewerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private lateinit var engine: Engine
    private lateinit var renderer: Renderer
    private lateinit var scene: Scene
    private lateinit var filamentView: com.google.android.filament.View

    private lateinit var swapChain: SwapChain


    private lateinit var assetLoader: AssetLoader
    private lateinit var resourceLoader: ResourceLoader
    private var filamentAsset: FilamentAsset? = null

    init {
        holder.addCallback(this)
    }

    fun surfaceCreatedOld(holder: SurfaceHolder) { // Override
        engine = Engine.create()
        renderer = engine.createRenderer()
        scene = engine.createScene()
        filamentView = engine.createView()
        filamentView.scene = scene

        val materialProvider = UbershaderLoader(engine)
        val assetLoader = AssetLoader(engine, materialProvider, EntityManager.get())
        val resourceLoader = ResourceLoader(engine)
        this.assetLoader = assetLoader
        this.resourceLoader = resourceLoader

        // Start a simple render loop (in a real app, consider a better scheduling approach)
        startRenderLoop()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        engine = Engine.create()
        renderer = engine.createRenderer()
        scene = engine.createScene()
        filamentView = engine.createView()

        // Create a SwapChain for this surface
        swapChain = engine.createSwapChain(holder.surface)

        // ... set up your camera, asset loaders, etc.
        startRenderLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Update the viewport to match the SurfaceView size
        filamentView.viewport = Viewport(0, 0, width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Clean up Filament resources
        filamentAsset?.let { assetLoader.destroyAsset(it) }
        //renderer.
        engine.destroyView(filamentView)
        engine.destroyScene(scene)
        engine.destroy()
    }

    /**
     * Load a glTF model from raw bytes and add it to the Filament scene.
     */
    fun loadGltfBytes(gltfBytes: ByteArray) {
        val asset = assetLoader.createAssetFromJson(ByteBuffer.wrap(gltfBytes))
        if (asset != null) {
            resourceLoader.loadResources(asset)
            scene.addEntities(asset.entities)
            filamentAsset = asset
        }
    }

    /**
     * Simple continuous render loop. Renders a frame ~60 times a second.
     */
    private fun startRenderLoop() {
        val self = this
        val runnable = object : Runnable {
            override fun run() {
                if (holder.surface.isValid) {
                    // Use a time stamp, e.g. System.nanoTime(), for frameTimeNanos
                    val frameTimeNanos = System.nanoTime()
                    if (renderer.beginFrame(swapChain, frameTimeNanos)) {
                        renderer.render(filamentView)
                        renderer.endFrame()
                    }
                }
                self.postDelayed(this, 16L) // ~60 FPS
            }
        }
        post(runnable)
    }

}
