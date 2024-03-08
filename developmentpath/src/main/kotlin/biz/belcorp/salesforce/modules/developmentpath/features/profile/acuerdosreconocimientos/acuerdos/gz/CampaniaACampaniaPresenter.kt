package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.ModificarAcuerdosUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniaACampaniaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel

class CampaniaACampaniaPresenter(
    private val obtenerCampaniaACampaniaUseCase: ObtenerCampaniaACampaniaUseCase,
    private val modificarAcuerdosUseCase: ModificarAcuerdosUseCase
) {

    lateinit var view: CampaniaCampaniaView

    fun obtener(zonaId: Long, rol: Rol) {
        obtenerCampaniaACampaniaUseCase
            .obtener(
                ObtenerCampaniaACampaniaUseCase.Request(zonaId, rol),
                ObtenerCampaniaCampaniaSubscriber(view)
            )
    }

    fun editar(acuerdoModel: AcuerdoModel) {
        modificarAcuerdosUseCase.editar(
            ModificarAcuerdosUseCase.EditarRequest(
                acuerdoId = acuerdoModel.id,
                nuevoContenido = acuerdoModel.descripcion,
                subscriber = EditarAcuerdoSubscriber()
            )
        )
    }

    fun eliminar(acuerdoModel: AcuerdoModel) {
        modificarAcuerdosUseCase.eliminar(
            ModificarAcuerdosUseCase.EliminarRequest(
                acuerdoId = acuerdoModel.id,
                subscriber = EliminarAcuerdoSubscriber()
            )
        )
    }

    private inner class EditarAcuerdoSubscriber : BaseCompletableObserver() {

        override fun onComplete() {
            view.alEditarAcuerdo()
        }
    }

    private inner class EliminarAcuerdoSubscriber : BaseCompletableObserver() {

        override fun onComplete() {
            view.alEliminarAcuerdo()
        }
    }
}
