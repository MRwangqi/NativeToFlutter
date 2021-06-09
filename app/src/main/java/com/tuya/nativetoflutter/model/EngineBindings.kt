package com.tuya.nativetoflutter.model

import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel

/**
 * This interface represents the notifications an EngineBindings may be receiving from the Flutter
 * instance.
 *
 * What methods this interface has depends on the messages that are sent over the EngineBinding's
 * channel in `main.dart`.  Messages that interact with the DataModel are handled automatically
 * by the EngineBindings.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
interface EngineBindingsDelegate {
    fun onNext()
}

/**
 * This binds a FlutterEngine instance with the DataModel and a channel for communicating with that
 * engine.
 *
 * Messages involving the DataModel are handled by the EngineBindings, other messages are forwarded
 * to the EngineBindingsDelegate.
 *
 * @see main.dart for what messages are getting sent from Flutter.
 */
class EngineBindings(
    delegate: EngineBindingsDelegate,
   val engineId: String
) :
    DataModelObserver {
    private val channel: MethodChannel
    private  val engine: FlutterEngine = FlutterEngineCache.getInstance().get(engineId)!!
    private val delegate: EngineBindingsDelegate = delegate

    init {
        channel = MethodChannel(engine.dartExecutor.binaryMessenger, "multiple-flutters")
    }

    /**
     * This setups the messaging connections on the platform channel and the DataModel.
     */
    fun attach() {
        DataModel.instance.addObserver(this)
        channel.invokeMethod("setCount", DataModel.instance.counter)
        channel.setMethodCallHandler { call, result ->
            Log.e("TAG", "attach: " + call.method)
            when (call.method) {
                "incrementCount" -> {
                    DataModel.instance.counter = DataModel.instance.counter + 1
                    result.success(null)
                }
                "next" -> {
                    this.delegate.onNext()
                    result.success(null)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    /**
     * This tears down the messaging connections on the platform channel and the DataModel.
     */
    fun detach() {
        // TODO: Uncomment after https://github.com/flutter/engine/pull/24644 is on stable.
        // engine.destroy();
        DataModel.instance.removeObserver(this)
        channel.setMethodCallHandler(null)
    }

    override fun onCountUpdate(newCount: Int) {
        channel.invokeMethod("setCount", newCount)
    }
}
