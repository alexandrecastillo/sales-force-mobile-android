package biz.belcorp.salesforce.core.data.repository.device

import android.os.Build
import biz.belcorp.salesforce.core.constants.Constant.APP_ID
import biz.belcorp.salesforce.core.data.preferences.ConfigSharedPreferences
import biz.belcorp.salesforce.core.data.repository.device.cloud.DeviceCloudStore
import biz.belcorp.salesforce.core.data.repository.device.cloud.DeviceRequestDto
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.repository.device.DeviceRepository
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class DeviceDataRepository(
    private val deviceCloudStore: DeviceCloudStore,
    private val configPreferences: ConfigSharedPreferences,
    private val uuid: String
) : DeviceRepository {

    override suspend fun saveToken(token: String, session: Sesion?) {
        with(configPreferences) {
            fcmToken = token
            fcmTokenPending = true
            session?.also {
                val request = createRequest(token, it)
                deviceId = deviceCloudStore.saveToken(request)
                fcmTokenPending = false
            }
        }
    }

    private fun createRequest(token: String, session: Sesion): DeviceRequestDto {
        return DeviceRequestDto(
            pais = session.pais?.codigoIso.orEmpty(),
            rolId = session.rol.id(),
            usuario = session.codeByRole,
            uuid = uuid,
            imei = uuid,
            modelo = Build.MODEL,
            version = Build.VERSION.RELEASE,
            topicoFCM = configPreferences.leaderFcmTopic,
            tokenFcm = token,
            appID = APP_ID
        )
    }

    override suspend fun syncTokenIfNeeded(session: Sesion?) {
        with(configPreferences) {
            if (fcmToken == null) {
                fcmToken = awaitToken()
                fcmTokenPending = true
            }
            if (fcmTokenPending) {
                saveToken(fcmToken ?: return, session)
            }
        }
    }

    private suspend fun awaitToken(): String =
        suspendCancellableCoroutine { cont ->
            FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener {
                    cont.resume(it.token)
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }

    override fun resetFcmTokenPending() {
        configPreferences.fcmTokenPending = true
    }
}
