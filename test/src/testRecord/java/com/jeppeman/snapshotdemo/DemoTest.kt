package com.jeppeman.snapshotdemo

import android.widget.LinearLayout
import android.widget.TextView
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.layoutlib.bridge.impl.RenderAction
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class DemoTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light"
    )

    object TextSizeProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues(): List<Int> = (20..30).toList()
    }

    @Test
    fun testXmlDefinedTextSize(
        @TestParameter(valuesProvider = TextSizeProvider::class) textSizeSp: Int
    ) {
        val layoutId = RenderAction.getCurrentContext().run {
            resources.getIdentifier("text_view_${textSizeSp}sp", "layout", packageName)
        }
        val view = paparazzi.inflate<LinearLayout>(layoutId)
        paparazzi.snapshot(view)
    }

    @Test
    fun testProgrammaticallyDefinedTextSize(
        @TestParameter(valuesProvider = TextSizeProvider::class) textSizeSp: Int
    ) {
        val view = paparazzi.inflate<LinearLayout>(R.layout.text_view_20sp).apply {
            findViewById<TextView>(R.id.text).textSize = textSizeSp.toFloat()
        }
        paparazzi.snapshot(view)
    }
}