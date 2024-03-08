package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidarPin {
    var pais: String? = null
    var solicitudPostulanteID: Int? = Constant.NUMERO_CERO
    var numeroDocumento: String? = null
    var pin: String? = null
}
