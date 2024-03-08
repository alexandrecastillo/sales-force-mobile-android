package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.MetaPersonalUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.model.MetaPersonalModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalView
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class MetaPersonalPresenter(
    private val view: MetaPersonalView,
    private val mapper: MetaPersonalMapper,
    private val metasUseCase: MetaPersonalUseCase
) {


    private var metas = mutableListOf<MetaPersonal>()
    private var metaAEliminar: MetaPersonal? = null

    companion object {
        const val LIMITE = 3
    }

    fun recuperarMetas(personaId: Long) = metasUseCase.listar(personaId, ListarSubscriber())

    fun guardarMeta(modelo: MetaPersonalModel, rol: Rol) {
        val meta = mapper.parse(modelo)
        decidirCrearOActualizar(meta, rol)
    }

    private fun decidirCrearOActualizar(meta: MetaPersonal, rol: Rol) {
        if (meta.metaId == -1L)
            metasUseCase.grabar(meta, rol, GrabarSubscriber())
        else
            metasUseCase.actualizar(meta, rol, ActualizarSubscriber())
    }

    fun eliminarMeta(modelo: MetaPersonalModel) {
        metaAEliminar = mapper.parse(modelo)
        metasUseCase.eliminar(metaAEliminar!!, EliminarSubscriber())
    }

    private fun validarCantidad() {
        validarLimite()
        contarMetas()
        decidirMostrarEdicion()
    }

    private fun validarLimite() =
        if (metas.size >= LIMITE)
            view.deshabilitarNueva()
        else
            view.habilitarNueva()

    private fun contarMetas() {
        view.pintarContador(metas.size, LIMITE)
    }

    private fun decidirMostrarEdicion() {
        if (metas.size == 0)
            view.mostrarCampoDeEdicion()
        else
            view.ocultarCampoDeEdicion()
    }

    private inner class ListarSubscriber : BaseSingleObserver<List<MetaPersonal>>() {

        override fun onSuccess(t: List<MetaPersonal>) {
            metas = t.toMutableList()
            validarCantidad()
            mostrarMetas(t)
        }
    }

    private fun mostrarMetas(metas: List<MetaPersonal>) {
        doAsync {
            val modelos = mapper.parse(metas)
            uiThread {
                view.mostrarMetas(modelos)
            }
        }
    }

    private inner class GrabarSubscriber : BaseSingleObserver<MetaPersonal>() {

        override fun onSuccess(t: MetaPersonal) {
            metas.add(t)
            validarCantidad()
            agregarMetaEnVista(t)
            view.mostrarMensaje("Se guardó correctamente.")
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view.mostrarMensaje("No se pudo grabar el registro.")
        }
    }

    private fun agregarMetaEnVista(meta: MetaPersonal) {
        doAsync {
            val modelo = mapper.parse(meta)
            uiThread {
                view.agregarMeta(modelo)
            }
        }
    }

    private inner class EliminarSubscriber : BaseCompletableObserver() {

        override fun onComplete() {
            metaAEliminar?.apply {
                metas.removeAll { it.metaId == this.metaId }
                validarCantidad()
                eliminarMetaEnVista(this)
            }
        }
    }

    private fun eliminarMetaEnVista(meta: MetaPersonal) {
        doAsync {
            val modelo = mapper.parse(meta)
            uiThread {
                view.eliminarMeta(modelo)
                view.mostrarMensaje("Se eliminó correctamente.")
            }
        }
    }

    private inner class ActualizarSubscriber : BaseSingleObserver<MetaPersonal>() {

        override fun onSuccess(t: MetaPersonal) {
            validarCantidad()
            agregarMetaEnVista(t)
            view.mostrarMensaje("Se modificó correctamente.")
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            view.mostrarMensaje("No se pudo grabar el registro.")
        }
    }
}
