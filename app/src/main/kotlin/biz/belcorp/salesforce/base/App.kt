package biz.belcorp.salesforce.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import androidx.multidex.MultiDex
import biz.belcorp.salesforce.base.di.injectModules
import biz.belcorp.salesforce.base.features.exception.UncaughtExceptionActivity
import biz.belcorp.salesforce.base.features.splash.SplashActivity
import biz.belcorp.salesforce.base.utils.caoc.Caoc
import biz.belcorp.salesforce.base.utils.caoc.config.BackgroundMode.BackgroundMode.BACKGROUND_MODE_SILENT
import biz.belcorp.salesforce.base.utils.caoc.config.CaocConfig
import biz.belcorp.salesforce.core.db.dbflow.DbFlow
import biz.belcorp.salesforce.core.db.objectbox.ObjectBox
import biz.belcorp.salesforce.core.di.features.DependenciesManager
import biz.belcorp.salesforce.core.utils.isNull
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import com.google.android.play.core.splitcompat.SplitCompat
import io.objectbox.exception.DbSchemaException
import org.koin.android.ext.android.inject
import java.io.File
import java.lang.reflect.Method

const val TAG = "APPLICATION"

open class App : Application() {
    var objectBoxDbRestored = false
    private val dependenciesManager by inject<DependenciesManager>()

    override fun onCreate() {
        super.onCreate()
        if (isMissingRequiredSplits()) return
        injectModules()
        initDbFlow()
        initObjectBox()
        initExceptionHandler()
        initEmojiCompat()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
        MultiDex.install(this)
    }

    protected open fun injectModules() {
        injectModules(this)
        dependenciesManager.loadFeaturesModules()
    }

    private fun initDbFlow() {
        DbFlow.init(this, isTesting())
    }

    private fun initObjectBox() {
        try {
            ObjectBox.init(this)
        }
        catch (objectBoxException: DbSchemaException){
            Log.e(TAG, objectBoxException.message.toString())
            initObjectBoxFromCatch()
        }
    }

    private fun initObjectBoxFromCatch(){
        val androidFilesDirectoryMethod: Method
        lateinit var androidFilesDirectory: File
        try {
            androidFilesDirectoryMethod = this.javaClass.getMethod("getFilesDir")
            androidFilesDirectory = androidFilesDirectoryMethod.invoke(this) as File
            if(androidFilesDirectory.isNull()){
                System.err.println("getFilesDir() returned null - retrying once...")
                androidFilesDirectory = androidFilesDirectoryMethod.invoke(this) as File
            }

        }
        catch (e: Exception){
            Log.e("DEBUG", e.message.toString())
        }

        //Get object box directory inside androidFilesDirectory
        val objectBoxFileDirectory = File(androidFilesDirectory, "objectbox")


        var files = objectBoxFileDirectory.listFiles()
        if(files.isNull()){   //NO HAY ARCHIVOS EN EL DIR DE OBJECT BOX
            Log.e("BOXSTORE", "No hay Archivos dentro del dir ")
        }else{                //HAY ARCHIVOS EN EL DIR DE OBJECT BOX
            Log.e("BOXSTORE", "Hay Archivos dentro del dir ")

            //Recorremos los files

            val fileInObjectBoxDirectory = files?.get(0)

            (fileInObjectBoxDirectory?.listFiles())?.forEachIndexed{ index, item ->

                Log.d("BOXSTORE", "index: $index  hay item: ${item.name}")

                //File exists?
                Log.d("BOXSTORE", "index: $index  item exists? result: ${item.exists()}")

                //File is directory?
                Log.d("BOXSTORE", "index: $index  item is directory? : ${item.isDirectory}")

                //File length
                Log.d("BOXSTORE", "index: $index  item length : ${item.length()}")

                if(item.delete()){
                    Log.d("BOXSTORE", "DELETED ITEM")
                }else{
                    Log.d("BOXSTORE", "NOT DELETED ITEM")
                }
            }
        }

        //Todos los files fueron borrados, se puede hacer init
        Log.d("BOXSTORE", "Init ObjectBox luego del borrado")
        objectBoxDbRestored = true
        ObjectBox.init(this)
    }



    private fun initExceptionHandler() {
        Caoc.install(this)
        CaocConfig.Builder.create()
            .backgroundMode(BACKGROUND_MODE_SILENT)
            .minTimeBetweenCrashesMs(2000)
            .errorActivity(UncaughtExceptionActivity::class.java)
            .restartActivity(SplashActivity::class.java)
            .apply()
    }

    private fun isMissingRequiredSplits(): Boolean {
        return MissingSplitsManagerFactory.create(this)
            .disableAppIfMissingRequiredSplits()
    }

    private fun initEmojiCompat() {
        val config = BundledEmojiCompatConfig(this)
            .setReplaceAll(true)
        EmojiCompat.init(config)
    }

    protected open fun isTesting(): Boolean {
        return false
    }

}
