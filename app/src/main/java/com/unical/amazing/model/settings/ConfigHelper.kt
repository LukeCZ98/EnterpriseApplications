package com.unical.amazing.model.settings

import android.content.Context
import com.unical.amazing.R
import java.util.Properties

object ConfigHelper {
    fun getTruststorePassword(context: Context): String {
        val properties = Properties()
        context.resources.openRawResource(R.raw.config).use { inputStream ->
            properties.load(inputStream)
        }
        return properties.getProperty("truststorePassword")
    }
}