package biz.belcorp.salesforce.core.data.preferences

import android.content.SharedPreferences
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.utils.putBoolean
import biz.belcorp.salesforce.core.utils.putString

class UserSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "UserPreferences"

        const val KEY_ROL = "rol"
        const val KEY_COD_ROL = "cod_rol"
        const val KEY_COD_USUARIO = "codUsuario"

        const val KEY_REGION = "region"
        const val KEY_ZONA = "zona"
        const val KEY_SECCION = "seccion"
        const val KEY_PAIS = "pais"
        const val KEY_COD_PAIS = "cod_pais"

        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_SECOND_NAME = "second_name"
        const val KEY_DOCUMENTO = "documento_identidad"
        const val KEY_COD_CONSULTORA = "codConsultora"
        const val KEY_USERNAME = "username"

        const val KEY_CUB = "cub"
        const val KEY_NIVEL = "nivel"
        const val KEY_TELEFONO_MOVIL = "telefono_movil"
        const val KEY_TELEFONO_FIJO = "telefono_fijo"
        const val KEY_EMAIL = "email"
        const val KEY_CONSULTORA_ID = "consultora_id"
        const val KEY_LATITUD = "latitud"
        const val KEY_LONGITUD = "longitud"
        const val KEY_COD_TERRITORIO = "codTerritorio"
        const val KEY_SECCION_GESTION_LIDER = "seccionGestionLider"
        const val KEY_GEOREFERENCIA = "georeferencia"
        const val KEY_IS_INSPIRA = "is_inspira"
        const val KEY_COUNT_INSPIRA_CLOSE = "count_inspira_close"

        //Used in SaleForceStatus
        const val KEY_PROFILE = "profile"
        const val KEY_LEVEL = "level"
        const val KEY_ACHIEVEMENT = "achievement"
        const val KEY_PRODUCTIVITY = "productivity"
        const val KEY_CLASSIFICATION = "classification"
        const val KEY_SUBCLASSIFICATION = "subclassification"



    }

    var codUsuario: String?
        get() = preferences.getString(KEY_COD_USUARIO, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_COD_USUARIO, value)

    var rol: String?
        get() = preferences.getString(KEY_ROL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_ROL, value)

    var codRol: String?
        get() = preferences.getString(KEY_COD_ROL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_COD_ROL, value)

    var region: String?
        get() = preferences.getString(KEY_REGION, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_REGION, value)

    var zona: String?
        get() = preferences.getString(KEY_ZONA, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_ZONA, value)

    var seccion: String?
        get() = preferences.getString(KEY_SECCION, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_SECCION, value)

    var pais: String?
        get() = preferences.getString(KEY_PAIS, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_PAIS, value)

    var codPais: String?
        get() = preferences.getString(KEY_COD_PAIS, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_COD_PAIS, value)

    var nombre: String?
        get() = preferences.getString(KEY_FIRST_NAME, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_FIRST_NAME, value)

    var apellido: String?
        get() = preferences.getString(KEY_LAST_NAME, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LAST_NAME, value)

    var apellidoMaterno: String?
        get() = preferences.getString(KEY_SECOND_NAME, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_SECOND_NAME, value)

    var documento: String?
        get() = preferences.getString(KEY_DOCUMENTO, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_DOCUMENTO, value)

    var codConsultora: String?
        get() = preferences.getString(KEY_COD_CONSULTORA, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_COD_CONSULTORA, value)

    var username: String?
        get() = preferences.getString(KEY_USERNAME, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_USERNAME, value)

    var cub: String?
        get() = preferences.getString(KEY_CUB, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_CUB, value)

    var nivel: String?
        get() = preferences.getString(KEY_NIVEL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_NIVEL, value)

    var celular: String?
        get() = preferences.getString(KEY_TELEFONO_MOVIL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_TELEFONO_MOVIL, value)

    var telefono: String?
        get() = preferences.getString(KEY_TELEFONO_FIJO, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_TELEFONO_FIJO, value)

    var correo: String?
        get() = preferences.getString(KEY_EMAIL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_EMAIL, value)

    var consultoraId: String?
        get() = preferences.getString(KEY_CONSULTORA_ID, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_CONSULTORA_ID, value)

    var latitud: String?
        get() = preferences.getString(KEY_LATITUD, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LATITUD, value)

    var longitud: String?
        get() = preferences.getString(KEY_LONGITUD, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LONGITUD, value)

    var codTerritorio: String?
        get() = preferences.getString(KEY_COD_TERRITORIO, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_COD_TERRITORIO, value)

    var seccionGestionLider: String?
        get() = preferences.getString(KEY_SECCION_GESTION_LIDER, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_SECCION_GESTION_LIDER, value)

    var georeferencia: String?
        get() = preferences.getString(KEY_GEOREFERENCIA, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_GEOREFERENCIA, value)

    var showInspiraPopup: Boolean
        get() = preferences.getBoolean(KEY_IS_INSPIRA, true)
        set(value) = preferences.putBoolean(KEY_IS_INSPIRA, value)

    var attemptInspira: Int
        get() = preferences.getInt(KEY_COUNT_INSPIRA_CLOSE, NUMBER_ZERO)
        set(value) {
            preferences.edit().putInt(KEY_COUNT_INSPIRA_CLOSE, value)
        }

    //USED IN SALE FORCE STATUS
    var profile: String?
        get() = preferences.getString(KEY_PROFILE, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_PROFILE, value)

    var level: String?
        get() = preferences.getString(KEY_LEVEL, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_LEVEL, value)

    var achievement: String?
        get() = preferences.getString(KEY_ACHIEVEMENT, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_ACHIEVEMENT, value)

    var productivity: String?
        get() = preferences.getString(KEY_PRODUCTIVITY, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_PRODUCTIVITY, value)

    var classification: String?
        get() = preferences.getString(KEY_CLASSIFICATION, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_CLASSIFICATION, value)

    var subclassification: String?
        get() = preferences.getString(KEY_SUBCLASSIFICATION, Constant.EMPTY_STRING)
        set(value) = preferences.putString(KEY_SUBCLASSIFICATION, value)



    fun clear() {
        codUsuario = Constant.EMPTY_STRING
        rol = Constant.EMPTY_STRING
        codRol = Constant.EMPTY_STRING
        region = Constant.EMPTY_STRING
        zona = Constant.EMPTY_STRING
        seccion = Constant.EMPTY_STRING
        pais = Constant.EMPTY_STRING
        codPais = Constant.EMPTY_STRING
        nombre = Constant.EMPTY_STRING
        apellido = Constant.EMPTY_STRING
        apellidoMaterno = Constant.EMPTY_STRING
        documento = Constant.EMPTY_STRING
        codConsultora = Constant.EMPTY_STRING
        username = Constant.EMPTY_STRING
        cub = Constant.EMPTY_STRING
        nivel = Constant.EMPTY_STRING
        celular = Constant.EMPTY_STRING
        telefono = Constant.EMPTY_STRING
        correo = Constant.EMPTY_STRING
        consultoraId = Constant.EMPTY_STRING
        latitud = Constant.EMPTY_STRING
        longitud = Constant.EMPTY_STRING
        codTerritorio = Constant.EMPTY_STRING
        seccionGestionLider = Constant.EMPTY_STRING
        georeferencia = Constant.EMPTY_STRING
        showInspiraPopup = false
        attemptInspira = NUMBER_ZERO
        profile = Constant.EMPTY_STRING
        level = Constant.EMPTY_STRING
        achievement = Constant.EMPTY_STRING
        productivity = Constant.EMPTY_STRING
        classification = Constant.EMPTY_STRING
        subclassification = Constant.EMPTY_STRING
    }

}
