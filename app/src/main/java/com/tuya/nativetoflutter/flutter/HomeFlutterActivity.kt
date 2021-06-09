package com.tuya.nativetoflutter.flutter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tuya.nativetoflutter.MainActivity
import com.tuya.nativetoflutter.R
import com.tuya.nativetoflutter.engine.EngineData
import com.tuya.nativetoflutter.model.EngineBindings
import com.tuya.nativetoflutter.model.EngineBindingsDelegate
import io.flutter.embedding.android.FlutterFragment

class HomeFlutterActivity : AppCompatActivity() , EngineBindingsDelegate {

    private val mainEngineBinding: EngineBindings by lazy {
        EngineBindings(this, EngineData.Page.home.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_flutter)

        val flutterFragment =
            FlutterFragment.withCachedEngine(mainEngineBinding.engineId)
                    .build<FlutterFragment>()

        supportFragmentManager.beginTransaction()
            .add(R.id.flutter_container, flutterFragment)
            .commit()

        mainEngineBinding.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainEngineBinding.detach()
    }


    override fun onNext() {
        // next page
        startActivity(Intent(this, MainFlutterActivity::class.java))
    }

}