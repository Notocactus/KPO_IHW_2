package util

import java.io.IOException
import java.util.Properties

class PropertiesUtil {
    companion object {
        private val PROPERTIES: Properties = Properties();

        init {
            loadProperties()
        }

        private fun loadProperties() {
            try {
                val inputStream = Companion::class.java.classLoader.getResourceAsStream("application.properties")
                inputStream.use {
                    PROPERTIES.load(inputStream)
                }
            } catch (e: IOException) {
                println(e.message)
            }

        }

        fun get(key: String): String = PROPERTIES.getProperty(key)

    }
}
