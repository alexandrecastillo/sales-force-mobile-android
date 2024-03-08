package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.MetaSociaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.mapper.MetaSociaModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo

class MetaSociaPresenter(
    private val view: CrearMetaSociaView,
    private val useCase: MetaSociaUseCase,
    private val mapper: MetaSociaModelMapper
) {

    private var modelo = MetaSociaModelo.crearModelo()

    fun realizarOperacion(personaId: Long, rol: Rol) {
        if (!modelo.esModoEdicion()) guardar(personaId, rol)
        else actualizar(personaId, rol)
    }

    private fun guardar(personaId: Long, rol: Rol) {
        val request = MetaSociaUseCase.Request(
            personaId = personaId,
            rol = rol,
            modelo = mapper.reverseParse(modelo),
            subscriberCompletable = GuardarMetaSubscriber()
        )
        useCase.guardar(request)
    }

    private fun actualizar(personaId: Long, rol: Rol) {
        val request = MetaSociaUseCase.Request(
            modelo = mapper.reverseParse(modelo),
            personaId = personaId,
            rol = rol,
            subscriberCompletable = EditarMetaSubscriber()
        )
        useCase.actualizar(request)
    }

    fun asignarDescripcion(descripcion: String) {
        modelo.descripcion = descripcion
        cambiarEstadoBoton()
    }

    fun asignarModelo(modelo: MetaSociaModelo) {
        this.modelo = modelo
        this.modelo.crearFecha()
    }

    fun cargarDatosEdicion() {
        if (modelo.esValido) view.cargarDatosMeta(modelo)
    }

    private fun cambiarEstadoBoton() {
        if (modelo.esValido) view.habilitarBotonGuardar()
        else view.deshabilitarBotonGuardar()
    }

    private fun notificarAVista() {
        view.limpiarDescripcion()
        view.cerrarVentana()
        view.notifcarExito()
    }

    private inner class GuardarMetaSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            notificarAVista()
        }

        override fun onError(e: Throwable) {
            view.mostrarMensajeErrorAlCrear()
        }
    }

    private inner class EditarMetaSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            notificarAVista()
        }

        override fun onError(e: Throwable) {
            view.mostrarMensajeErrorAlEditar()
        }
    }
}
