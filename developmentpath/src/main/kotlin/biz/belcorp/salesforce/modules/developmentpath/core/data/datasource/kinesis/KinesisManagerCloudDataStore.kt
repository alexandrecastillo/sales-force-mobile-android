package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.kinesis

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.CognitoCredentialsProvider
import com.amazonaws.mobileconnectors.kinesis.kinesisrecorder.KinesisRecorder
import com.amazonaws.regions.Regions
import com.google.gson.Gson

class KinesisManagerCloudDataStore(private val context: Context,
                                    private val hardwareInfoRetriever: HardwareInfoRetriever
) {

    private var recorder: KinesisRecorder? = null
    private var stremBaseName: String? = null

    private var namePlication = "APPFFVV"
    private var versionName: String? = null
    private val isOffline
        get() =
            hardwareInfoRetriever.get().currentNetworkStatus == NetworkStatus.DISCONNECTED
    private val buildVariant
        get() = hardwareInfoRetriever.get().buildVariant

    companion object {

        const val ID_PPR = "us-east-1:9bc53455-f6ec-4809-a1bb-02818540154a"
        const val ID_QAS = "us-east-1:77066302-ee9d-4c50-b999-e34b95444ee6"
        const val STRING_NAME_PRD = "log-usabilidad-stream-prd"
        const val STRING_NAME_QAS = "log-usabilidad-stream"
    }

    init {
        build()
    }

    private fun build() {
        when (buildVariant) {
            BuildVariant.RELEASE -> initAWSProvider(ID_PPR, STRING_NAME_PRD)
            else -> initAWSProvider(ID_QAS, STRING_NAME_QAS)
        }
    }

    private fun initAWSProvider(idAWS: String, strem: String) {
        val provider: AWSCredentialsProvider = CognitoCredentialsProvider(idAWS, Regions.US_EAST_1)
        recorder = KinesisRecorder(context.getDir(idAWS, 0), Regions.US_EAST_1, provider)
        stremBaseName = strem
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, 0)
        versionName = info.versionName.split("-").first()
    }

    fun enviarDatosGrabados(logKinesis: KinesisManagerUseCase.LogKinesis): String {
        build()
        return enviar(logKinesis)
    }

    private fun enviar(logKinesis: KinesisManagerUseCase.LogKinesis): String {

        logKinesis.apply {
            aplicacion = namePlication
            version = versionName
            offline = isOffline
            dispositivoId = deviceId(context)
        }

        val logString = Gson().toJson(logKinesis)
        recorder?.saveRecord(logString, stremBaseName)
        try {
            recorder?.submitAllRecords()
        } catch (e: Exception) {
            Logger.loge("KINESIS", e.localizedMessage, e)
            return "OFFLINE: $logString"
        }

        return logString
    }

    @SuppressLint("HardwareIds")
    private fun deviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

}
