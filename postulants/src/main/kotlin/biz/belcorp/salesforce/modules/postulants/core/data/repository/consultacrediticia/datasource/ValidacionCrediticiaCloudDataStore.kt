package biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.datasource

import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticia2Entity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.ConsultaCrediticiaInternaEntity
import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.CrediticiaDeudaExterna
import biz.belcorp.salesforce.modules.postulants.core.data.network.ValidacionCrediticiaRestApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import io.reactivex.Single

class ValidacionCrediticiaCloudDataStore(
    private val validacionCrediticiaApi: ValidacionCrediticiaRestApi
) {

    fun consultaCrediticiaDeudaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ConsultaCrediticiaInternaEntity> {
        return validacionCrediticiaApi.getConsultaCrediticiaDeudaInterna(pais, documentoIdentidad)
    }

    fun consultaCrediticiaDeudaExterna(
        crediticiaDeudaExterna: CrediticiaDeudaExterna
    ): Single<ConsultaCrediticia2Entity> {
        val params = mutableMapOf<String, String>()
        params["login"] = crediticiaDeudaExterna.username.orEmpty()
        params["codigoISO"] = crediticiaDeudaExterna.codigoISO.orEmpty()
        params["numeroDocumento"] = crediticiaDeudaExterna.documentoIdentidad.orEmpty()
        params["apellido"] = crediticiaDeudaExterna.apellido.orEmpty()
        params["codRegion"] = crediticiaDeudaExterna.codRegion.orEmpty()
        params["codZona"] = crediticiaDeudaExterna.codZona.orEmpty()
        params["codSeccion"] = crediticiaDeudaExterna.codSeccion.orEmpty()
        params["tipoIdentificacion"] = crediticiaDeudaExterna.tipoIdentificacion
        return validacionCrediticiaApi.getConsultaCrediticiaDeudaExterna(params)
    }

    


    fun obtenerSolicitudPostulanteEstados(
        codigoISO: String,
        solicitudPostulanteId: String
    ): Single<SolicitudPostulanteEstadosResponse> {
        return validacionCrediticiaApi.obtenerSolicitudPostulanteEstados(codigoISO, solicitudPostulanteId)
    }

}
