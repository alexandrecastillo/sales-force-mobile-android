package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource

import android.os.Build
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.utils.Logger
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import java.util.*


object PostulantsLogger {

    private const val STEP_TWO = 2
    private const val TAG = "Postulants"
    private const val MSG_1 = "List"
    private const val MSG_2 = "Entity"

    fun log(postulants: List<PostulanteEntity>) {
        try {
            val log = postulants.mapNotNull { createObject(it) }.takeIf { it.isNotEmpty() }
            val json = log?.let { Json.encodeToString(ListSerializer(PostulantLog.serializer()), log) } ?: return
            Logger.e(TAG, MSG_1, Exception(json))
        } catch (e: Exception) {
            Logger.e(TAG, MSG_1, e)
        }
    }

    fun log(postulant: PostulanteEntity) {
        try {
            val log = createObject(postulant)
            val json = log?.let { Json.encodeToString(PostulantLog.serializer(), it) } ?: return
            Logger.e(TAG, MSG_2, Exception(json))
        } catch (e: Exception) {
            Logger.e(TAG, MSG_2, e)
        }
    }

    private fun createObject(postulante: PostulanteEntity): PostulantLog? = with(postulante) {
        if (!postulante.hasErrors()) return null
        return PostulantLog(
            country = pais,
            zone = codigoZona,
            section = codigoSeccion,
            email = correoElectronico,
            birthday = fechaNacimiento,
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            locale = Locale.getDefault().toString()
        )
    }

    private fun PostulanteEntity.hasErrors(): Boolean {
        return paso >= STEP_TWO &&
            (correoElectronico.isNullOrBlank() || fechaNacimiento.isNullOrBlank())
    }

    @Serializable
    data class PostulantLog(
        @SerialName("country")
        val country: String? = Constant.EMPTY_STRING,
        @SerialName("zone")
        val zone: String? = Constant.EMPTY_STRING,
        @SerialName("section")
        val section: String? = Constant.EMPTY_STRING,
        @SerialName("email")
        val email: String? = Constant.EMPTY_STRING,
        @SerialName("birthday")
        val birthday: String? = Constant.EMPTY_STRING,
        @SerialName("manufacturer")
        val manufacturer: String? = Constant.EMPTY_STRING,
        @SerialName("model")
        val model: String? = Constant.EMPTY_STRING,
        @SerialName("locale")
        val locale: String? = Constant.EMPTY_STRING
    )

}
