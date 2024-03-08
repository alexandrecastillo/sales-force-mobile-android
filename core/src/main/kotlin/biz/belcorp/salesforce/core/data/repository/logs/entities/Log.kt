package biz.belcorp.salesforce.core.data.repository.logs.entities

import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ACTION
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.AMBIENTE
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.CAMPANIA
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.CATEGORIA
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.CODIGO_CONSULTORA
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.CUB
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ESTADO_CONEXION
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.LABEL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.NOMBRE_PUSH_NOTIFICACION
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.PAIS
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.PAIS_ACTUAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.PAIS_PRINCIPAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.PERIODO
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.REGION
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.REGION_ACTUAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.REGION_PRINCIPAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ROL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.SCREEN_NAME
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.SECCION_ACTUAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.TIPO_INGRESO
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ZONA
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ZONA_ACTUAL
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag.ZONA_PRINCIPAL
import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Log(@SerializedName(AMBIENTE)
               val ambiente: String? = null,
               @SerializedName(SCREEN_NAME)
               val screeName: String? = null) : Serializable

class Pantalla(@SerializedName(CAMPANIA)
               val campania: String? = null,
               @SerializedName(PAIS_PRINCIPAL)
               val paisPrincipal: String? = null,
               @SerializedName(REGION_PRINCIPAL)
               val regionPrincipal: String? = null,
               @SerializedName(ZONA_PRINCIPAL)
               val zonaPrincipal: String? = null,
               @SerializedName(PERIODO)
               val periodo: String? = null,
               @SerializedName(CUB)
               val cub: String? = null,
               @SerializedName(ROL)
               val rol: String? = null,
               @SerializedName(PAIS_ACTUAL)
               val paisActual: String? = null,
               @SerializedName(REGION_ACTUAL)
               val regionActual: String? = null,
               @SerializedName(ZONA_ACTUAL)
               val zonaActual: String? = null,
               @SerializedName(SECCION_ACTUAL)
               val secccionActual: String? = null,
               @SerializedName(ESTADO_CONEXION)
               val estadoConexion: String? = null,
               @SerializedName(CODIGO_CONSULTORA)
               val codigoConsultora: String? = null,
               screenName: String? = null,
               ambiente: String? = null) : Log(ambiente, screenName)

class Evento(@SerializedName(CATEGORIA)
             val categoria: String? = null,
             @SerializedName(ACTION)
             val action: String? = null,
             @SerializedName(LABEL)
             val label: String? = null,
             screenName: String? = null,
             ambiente: String? = null) : Log(ambiente, screenName)

class EventoPush(@SerializedName(CAMPANIA)
                 val campania: String? = null,
                 @SerializedName(PAIS)
                 val pais: String? = null,
                 @SerializedName(REGION)
                 val region: String? = null,
                 @SerializedName(ZONA)
                 val zona: String? = null,
                 @SerializedName(ROL)
                 val rol: String? = null,
                 @SerializedName(TIPO_INGRESO)
                 val tipoIngreso: String? = null,
                 @SerializedName(NOMBRE_PUSH_NOTIFICACION)
                 val nombrePushNotification: String? = null,
                 screenName: String? = null,
                 ambiente: String? = null) : Log(ambiente, screenName)
