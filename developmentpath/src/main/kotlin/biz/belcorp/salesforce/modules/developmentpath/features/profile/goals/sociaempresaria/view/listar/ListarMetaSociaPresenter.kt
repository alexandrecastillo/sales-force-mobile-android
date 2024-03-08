package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.MetaSociaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.mapper.MetaSociaModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSocialModeloContendor
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ListarMetaSociaPresenter(
    private val view: ListarMetaSociaView,
    private val useCase: MetaSociaUseCase,
    private val mapper: MetaSociaModelMapper
) {

    private var modelo = MetaSociaModelo.crearModelo()
    private val modeloContendor = MetaSocialModeloContendor.crearModelo()

    fun listar(personaId: Long, rol: Rol) {
        val request = MetaSociaUseCase.Request(
            personaId = personaId,
            rol = rol,
            subscriberListSingle = ListarMetaSubscriber()
        )
        useCase.listar(request)
    }

    fun eliminar(personaId: Long, rol: Rol) {
        val request = MetaSociaUseCase.Request(
            modelo = mapper.reverseParse(modelo),
            personaId = personaId,
            rol = rol,
            subscriberCompletable = EliminarMetaSubscriber()
        )
        useCase.eliminar(request)
    }

    fun asignarModelo(modelo: MetaSociaModelo) {
        this.modelo = modelo
    }

    private fun puedoMostrarMetas() {
        if (modeloContendor.metas.isNotEmpty()) {
            view.ocultarDescripcionNoHayMetas()
            view.mostrarContenedorMetas()
            view.mostrarContadorMetas()
            view.cargarTotalContadorMetas(modeloContendor.contadorMetas)
        } else {
            view.ocultarContenedorMetas()
            view.ocultarContadorMetas()
            view.mostrarDescripcionNoHayMetas()
        }
    }

    private fun puedoMostrarBotonAgregar() {
        if (modeloContendor.puedeCrearMeta) view.mostrarBotonCrear()
        else view.ocultarBotonCrear()
    }

    private inner class ListarMetaSubscriber : BaseSingleObserver<List<MetaSocia>>() {
        override fun onSuccess(t: List<MetaSocia>) {
            doAsync {
                modeloContendor.metas = t.map { mapper.parse(it) }
                uiThread {
                    view.mostrarDatos(modeloContendor.metas)
                    puedoMostrarMetas()
                    puedoMostrarBotonAgregar()
                }
            }
        }
    }

    private inner class EliminarMetaSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            view.notificarExitoAlEliminar()
        }

        override fun onError(e: Throwable) {
            view.mostrarMensajeError()
        }
    }
}
