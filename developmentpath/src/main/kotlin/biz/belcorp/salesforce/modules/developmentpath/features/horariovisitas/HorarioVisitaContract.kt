package biz.belcorp.salesforce.modules.developmentpath.features.horariovisitas

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import io.reactivex.Completable

interface HorarioVisitaContract {
    interface View {
        fun mostrarData(data: List<HorarioVisitaModel>)
    }

    interface Presenter {
        fun obtener(consultoraId: Long)

        fun saveOffLineHorarioSeleccionado(personaId: Long, item: HorarioVisitaModel)

        fun getConsultant(consultoraId: Int) : ConsultoraEntity?

    }
}
