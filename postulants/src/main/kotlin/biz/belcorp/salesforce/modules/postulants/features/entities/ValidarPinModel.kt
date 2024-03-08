package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidarPinModel {
    var pais: String? = null
    var solicitudPostulanteID: Int? = Constant.NUMERO_CERO
    var numeroDocumento: String? = null
    var PIN: String? = null
}

