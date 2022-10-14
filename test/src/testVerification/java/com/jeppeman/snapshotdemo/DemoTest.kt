
package com.jeppeman.snapshotdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
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
       paparazzi.snapshot {
           Column {
               Text(
                   text = stringResource(id = R.string.bacon_ipsum),
                   fontFamily = FontFamily.SansSerif,
                   fontSize = textSizeSp.sp,
                   letterSpacing = 0f.em,
                   color = Color.Blue
               )
               Box(modifier = Modifier.fillMaxSize().background(color = Color.Yellow))
           }
       }
    }

    @Test
    fun testProgrammaticallyDefinedTextSize(
        @TestParameter(valuesProvider = TextSizeProvider::class) textSizeSp: Int
    ) {
        paparazzi.snapshot {
            Column {
                Text(
                    text = stringResource(id = R.string.bacon_ipsum),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = textSizeSp.sp,
                    letterSpacing = 0f.em,
                    color = Color.Blue
                )
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Yellow))
            }
        }
    }
}
