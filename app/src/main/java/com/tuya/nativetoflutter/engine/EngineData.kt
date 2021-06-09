package com.tuya.nativetoflutter.engine

import com.tuya.nativetoflutter.App
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor

object EngineData {

    enum class Page {
        // 首页
        main,
        home
    }


    lateinit var enginesGroup: FlutterEngineGroup

    @JvmStatic
    fun init(app: App) {
        enginesGroup = FlutterEngineGroup(app)

        // 提前初始化 engine
        Page.values().map {
            createFlutterEngine(app, it.name)
        }
    }


    /**
     * engineId 和 entrypoint 一样
     */
    private fun createFlutterEngine(app: App, entrypoint: String) {
        val dartEntryPoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), entrypoint
            )

        // todo dartEntryPoint 不能为大写
        val engine = enginesGroup.createAndRunEngine(app, dartEntryPoint)
        // Configure an initial route. 配置路由
        engine.navigationChannel.setInitialRoute("home") // 指定跳转的路由界面，缓存后的 engine 对路由无效
        FlutterEngineCache.getInstance().put(entrypoint, engine)
    }
}