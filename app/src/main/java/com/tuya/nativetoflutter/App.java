package com.tuya.nativetoflutter;

import android.app.Application;

import com.tuya.nativetoflutter.engine.EngineData;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.embedding.engine.dart.DartExecutor;

public class App extends Application {





    @Override
    public void onCreate() {
        super.onCreate();


        EngineData.init(this);

//        // 提前初始化指定的 engineId 的 FlutterEngine
//        // Instantiate a FlutterEngine.
//        flutterEngine = new FlutterEngine(this);
//        // Configure an initial route.
//        flutterEngine.getNavigationChannel().setInitialRoute("home");
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        FlutterEngineCache.getInstance().put(HOME_ID, flutterEngine);
    }
}
