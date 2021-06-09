package com.tuya.nativetoflutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tuya.nativetoflutter.flutter.MainFlutterActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs

/**
 * doc {https://github.com/flutter/samples/blob/master/add_to_app/multiple_flutters/multiple_flutters_android/app/src/main/java/dev/flutter/multipleflutters/DoubleFlutterActivity.kt}
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goFlutter(view: View) {
        val intent = Intent()
        intent.setClass(this@MainActivity, MainFlutterActivity::class.java)
        startActivity(intent)
    }


    fun goFlutterActivity(view: View) {
        val intent = FlutterActivity
            .withNewEngine()
            // 背景颜色，透明 or 不透明
            .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
            // 指定跳转的路由
            .initialRoute("home")
            .build(this)
        startActivity(intent)
    }
}