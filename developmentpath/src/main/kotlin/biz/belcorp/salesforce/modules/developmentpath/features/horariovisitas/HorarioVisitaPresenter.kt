package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import android.util.Log
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.horariovisita.HorarioVisitaUseCase

class HorarioVisitaPresenter(
    private val view: HorarioVisitaContract.View,
    private val useCase: HorarioVisitaUseCase,
    private val horarioVisitaModelMapper: HorarioVisitaModelMapper,
    private val horarioVisitaConsultoraModelMapper: HorarioVisitaConsultoraModelMapper
) : HorarioVisitaContract.Presenter {

    override fun obtener(consultoraId: Long) {
        view.mostrarData(horarioVisitaModelMapper.map(useCase.obtenerHorarios(consultoraId)))
    }

    override fun saveOffLineHorarioSeleccionado(personaId: Long, item: HorarioVisitaModel) {

        val session = useCase.getConsultant(personaId.toInt())

        val horarioConsultora = HorarioVisitaConsultoraModel().apply {
            horarioVisitaId = item.horarioVisitaId
            consultoraId = personaId
            region = session?.region ?: Constant.EMPTY_STRING
            zona = session?.zona ?: Constant.EMPTY_STRING
            seccion = session?.seccion ?: Constant.EMPTY_STRING
            activo = item.activo
            enviado = false
        }

        useCase.saveHorarioOffLline(
            horarioVisitaConsultoraModelMapper.reverseMap(
                horarioConsultora
            ), SaveOfflineSubscriber()
        )
    }

    override fun getConsultant(consultoraId: Int): ConsultoraEntity? {
        return useCase.getConsultant(consultoraId)
    }

    /*Observables*/
    private inner class SaveOfflineSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            Log.i("DBFlow", "Datos actualizados exitosamente")
        }
    }
}
