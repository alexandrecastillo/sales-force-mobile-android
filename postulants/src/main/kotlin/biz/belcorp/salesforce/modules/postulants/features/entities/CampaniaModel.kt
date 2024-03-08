package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant
import java.io.Serializable
import java.util.*

class CampaniaModel : Serializable {
    var codigo: String = Constant.EMPTY_STRING
    var nombreCorto: String = Constant.EMPTY_STRING
    var inicio: Date? = null
    var fin: Date? = null
    var inicioFacturacion: Date? = null
    var orden: Int = Constant.NUMERO_CERO
    var esPrimerDiaFacturacion: Boolean = false
}
