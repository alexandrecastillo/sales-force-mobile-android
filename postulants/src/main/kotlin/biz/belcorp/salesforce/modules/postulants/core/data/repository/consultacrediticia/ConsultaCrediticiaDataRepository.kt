package biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia

import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.CrediticiaDeudaExterna
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.datasource.ValidacionCrediticiaCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.mappers.ValidacionCrediticiaEntityDataMapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaExterna
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.ValidacionCrediticiaInterna
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ConsultaCrediticiaRepository
import io.reactivex.Single

class ConsultaCrediticiaDataRepository(
    private val validacionCrediciaCloudStore: ValidacionCrediticiaCloudDataStore,
    private val validacionCrediticiaEntityDataMapper: ValidacionCrediticiaEntityDataMapper
) : ConsultaCrediticiaRepository {

    override fun validacionCrediticiaInterna(
        pais: String,
        documentoIdentidad: String
    ): Single<ValidacionCrediticiaInterna> {
        return validacionCrediciaCloudStore.consultaCrediticiaDeudaInterna(pais, documentoIdentidad)
            .map {
                validacionCrediticiaEntityDataMapper.parseValidacionCrediticiaInterna(it)
            }
    }

    override fun validacionCrediticiaExterna(
        username: String, codigoISO: String, documentoIdentidad: String, codRegion: String,
        codZona: String, tipoDocumento: String, codSeccion: String, apellido: String
    ): Single<ValidacionCrediticiaExterna> {
        val crediticiaExterna = CrediticiaDeudaExterna()
        crediticiaExterna.username = username
        crediticiaExterna.apellido = username
        crediticiaExterna.codigoISO = codigoISO
        crediticiaExterna.documentoIdentidad = documentoIdentidad
        crediticiaExterna.codRegion = codRegion
        crediticiaExterna.codZona = codZona
        crediticiaExterna.codSeccion = codSeccion
        crediticiaExterna.tipoIdentificacion = tipoDocumento
        crediticiaExterna.apellido = apellido

        return validacionCrediciaCloudStore.consultaCrediticiaDeudaExterna(crediticiaExterna)
            .map {
                validacionCrediticiaEntityDataMapper.parseValidacionCrediticiaExterna(codigoISO, it)
            }
    }

}
