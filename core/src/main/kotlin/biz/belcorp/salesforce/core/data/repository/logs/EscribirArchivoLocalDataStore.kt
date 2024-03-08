package biz.belcorp.salesforce.core.data.repository.logs

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import biz.belcorp.salesforce.core.BuildConfig
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.*
import java.io.*
import java.util.*

class EscribirArchivoLocalDataStore(val context: Context) {

    fun guardarLog(tipoLog: String, log: String) {
        try {
            if (isDebug() && isExternalStorageWritable) {
                crearCarpetaSiNoExiste()
                val nombreArchivo = obtenerNombreDeArchivo()
                val archivoActual = leerArchivo(nombreArchivo)
                var nuevoLog = "${Date().string(formatLongInverted)} $LOG_FOO $tipoLog$log"
                nuevoLog =
                    nuevoLog.takeIf { archivoActual.isEmpty() } ?: "$archivoActual \n$nuevoLog"
                val nuevoArchivo = File(nombreArchivo)
                val outputStream = FileOutputStream(nuevoArchivo)
                outputStream.write(nuevoLog.toByteArray())
                outputStream.close()
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(nuevoArchivo.absolutePath),
                    null
                ) { _, _ -> }
            }
        } catch (ex: Exception) {
            Logger.e(ex)
        }
    }

    private fun leerArchivo(nombreArchivo: String): String {
        return if (isDebug() && isExternalStorageReadable) {
            try {
                val br = BufferedReader(FileReader(nombreArchivo))

                var currentLine: String? = null
                val stringBuilder = StringBuilder()

                while ({ currentLine = br.readLine(); currentLine }() != null) {
                    stringBuilder.append(currentLine)
                    stringBuilder.append(System.getProperty("line.separator"))
                }
                return stringBuilder.toString()
            } catch (e: FileNotFoundException) {
                Constant.EMPTY_STRING
            }
        } else {
            Constant.EMPTY_STRING
        }
    }

    private fun crearCarpetaSiNoExiste() {
        val directory = File(
            Environment.getExternalStorageDirectory(),
            FOLDER_NAME
        )
        if (!directory.exists()) crearArchivoYObtener(directory)
    }

    private fun crearArchivoYObtener(directory: File): File {
        directory.mkdir()
        return directory
    }

    private fun obtenerNombreDeArchivo(): String {
        return "${Environment.getExternalStorageDirectory()}$FOLDER_NAME" +
            "$LOG_NAME${Date().string(formatShortInverted)}$LOG_EXTENSION"
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state &&
                context.isWriteExternalStoragePermissionGranted()
        }

    private val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    private fun isDebug() = BuildConfig.DEBUG

    companion object {

        private const val LOG_NAME = "analytics_log_"
        private const val FOLDER_NAME = "/com.belcorp.ffvv/"
        private const val LOG_EXTENSION = ".txt"
        private const val LOG_FOO = "DEBUG AnalyticsLogger - "

    }

}
