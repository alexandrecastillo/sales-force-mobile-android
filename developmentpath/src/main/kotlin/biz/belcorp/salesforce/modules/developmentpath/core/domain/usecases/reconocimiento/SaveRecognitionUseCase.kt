package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.notification.RddRecognitionData
import biz.belcorp.salesforce.messaging.core.domain.entities.RddRecognitionNotification
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.ReconocimientoASuperior
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.reconocimientos.ReconocimientoRepository


class SaveRecognitionUseCase(
    private val recognitionRepository: ReconocimientoRepository
) {

    suspend fun saveRecogniton(notification: RddRecognitionNotification) {
        val recognition = createRecognition(notification.data ?: return)
        recognitionRepository.saveRecognition(recognition)
    }

    private fun createRecognition(data: RddRecognitionData) = with(data) {
        ReconocimientoASuperior(
            idReconocimiento = id.toLong(),
            idPersonaReconoce = personId,
            rolReconoce = Rol.Builder.construir(personRol),
            idPersonaReconocida = personRecognizedId,
            rolReconocida = Rol.Builder.construir(personRecognizedRole),
            nombreReconocida = personRecognizedName,
            campania = campaign,
            valoracion = Constant.NEGATIVE_NUMBER_ONE,
            comentarios = Constant.EMPTY_STRING,
            pendienteReconocimiento = true
        )
    }

}
