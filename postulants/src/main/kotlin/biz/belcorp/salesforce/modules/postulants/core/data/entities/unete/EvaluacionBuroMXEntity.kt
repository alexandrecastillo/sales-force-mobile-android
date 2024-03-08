package biz.belcorp.salesforce.modules.postulants.core.data.entities.unete

import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class EvaluacionBuroMXEntity {
    var postulante: PostulanteEntity? = null
    var fechaNacimiento: String = Constant.EMPTY_STRING
    var estado: String = Constant.EMPTY_STRING
    var ciudad: String = Constant.EMPTY_STRING
    var direccion: String = Constant.EMPTY_STRING
    var tarjetaDeCredito: String = Constant.EMPTY_STRING
    var creditoHipotecario: String = Constant.EMPTY_STRING
    var creditoAutomotriz: String = Constant.EMPTY_STRING
}
