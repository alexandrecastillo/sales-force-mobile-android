package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaExterna
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaInterna
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import io.reactivex.Single

interface ConsultaCrediticiaRepository {

    fun validacionCrediticiaInterna(
        pais: String, documentoIdentidad: String
    ): Single<ValidacionCrediticiaInterna>

    fun validacionCrediticiaExterna(
        username: String,
        codigoISO: String,
        documentoIdentidad: String,
        codRegion: String,
        codZona: String,
        tipoDocumento: String,
        codSeccion: String,
        apellido: String = Constant.EMPTY_STRING
    ): Single<ValidacionCrediticiaExterna>

}
