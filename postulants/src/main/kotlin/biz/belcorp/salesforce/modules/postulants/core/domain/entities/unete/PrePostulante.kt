package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant


class PrePostulante : BasePostulante() {

    var solicitudPrePostulanteID: Int = Constant.NUMERO_CERO
    var nombres: String? = Constant.EMPTY_STRING
    var fechaModificacion: String? = Constant.EMPTY_STRING
    var rechazo: String? = Constant.EMPTY_STRING
    var ipOrigen: String? = Constant.EMPTY_STRING
    var zonificacion: String? = Constant.EMPTY_STRING
    var campaniaDeRegistro: Int? = Constant.NUMERO_CERO
    var codigoRegion: String? = Constant.EMPTY_STRING
    var estadoPrePostulante: String? = Constant.EMPTY_STRING
    var flagConsultoraDigital: String? = Constant.EMPTY_STRING
}
