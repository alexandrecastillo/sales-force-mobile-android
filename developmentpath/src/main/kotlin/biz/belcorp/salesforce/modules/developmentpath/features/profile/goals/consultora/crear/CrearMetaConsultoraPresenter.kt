package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.TipoMeta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.AdministrarMetaConsultoraUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.ObtenerListadoCampanaFinUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.metas.TipoMetaUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class CrearMetaConsultoraPresenter(
    private val view: CrearMetaConsultoraView,
    private val useCase: TipoMetaUseCase,
    private val obtenerListadoCampanaFinUseCase: ObtenerListadoCampanaFinUseCase,
    private val administrarMetaConsultoraUseCase: AdministrarMetaConsultoraUseCase
) {

    val model: CrearMetaConsultoraModel = CrearMetaConsultoraModel.inicializar()

    fun guardar(personaId: Long) {
        val request = armarRequest(personaId)
        administrarMetaConsultoraUseCase.guardar(request)
    }

    fun cambiarMonto(monto: String) {
        model.monto = monto
        repintarBotonGuardar()
    }

    fun cambiarComentario(comentario: String) {
        model.comentario = comentario
    }

    fun seleccionarTipoMeta(indice: Int) {
        model.indiceTipoMetaSeleccionado = indice
    }

    fun seleccionarCampaniaInicio(indice: Int) {
        model.indiceCampaniaInicioSeleccionada = indice
    }

    fun seleccionarCampaniaFin(indice: Int) {
        model.indiceCampaniaFinSeleccionada = indice
    }

    private fun repintarBotonGuardar() {
        if (model.esValido) {
            view.habilitarBotonGuardar()
        } else {
            view.deshabilitarBotonGuardar()
        }
    }

    private fun armarRequest(personaId: Long): AdministrarMetaConsultoraUseCase.GuardarRequest {
        return AdministrarMetaConsultoraUseCase.GuardarRequest(
            personaId = personaId,
            idTipoMeta = model.tipoMetaSeleccionado.id,
            descripcionMeta = model.tipoMetaSeleccionado.descripcion,
            comentario = model.comentario,
            monto = model.monto,
            campaniaInicio = model.campaniaInicioSeleccionada,
            campanaFin = model.campaniaFinSeleccionada,
            subscriber = GuardarMetaConsultoraSubscriber()
        )
    }

    fun obtenerCampanias(personaId: Long, rol: Rol) {
        val params = ObtenerListadoCampanaFinUseCase.Params(
            personaId = personaId,
            rol = rol,
            subscriber = ListaCampaniasFinSubscriber()
        )
        obtenerListadoCampanaFinUseCase.obtener(params)
    }

    fun obtenerTipoMetas() {
        useCase.obtener(TiposMetasSubscriber())
    }

    private inner class GuardarMetaConsultoraSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            doAsync {
                uiThread {
                    view.comunicarExito()
                    view.cerrar()
                }
            }
        }
    }

    private inner class ListaCampaniasFinSubscriber : BaseSingleObserver<List<String>>() {
        override fun onSuccess(t: List<String>) {
            doAsync {
                model.campaniasInicio = t
                model.campaniasFin = t

                if (t.isNotEmpty()) {
                    model.indiceCampaniaInicioSeleccionada = Constant.NUMERO_CERO
                    model.indiceCampaniaFinSeleccionada = Constant.NUMERO_CERO
                }

                uiThread {
                    pintarCampanias()
                    repintarBotonGuardar()
                }
            }
        }
    }

    private fun pintarCampanias() {
        view.mostrarListaCampaniasInicio(model.campaniasInicio)
        view.mostrarListaCampaniasFin(model.campaniasFin)
    }

    private inner class TiposMetasSubscriber : BaseSingleObserver<List<TipoMeta>>() {
        override fun onSuccess(t: List<TipoMeta>) {
            doAsync {
                model.tiposMeta = t.map { mapearTipoMeta(it) }
                if (t.isNotEmpty()) {
                    model.indiceTipoMetaSeleccionado = Constant.NUMERO_CERO
                }
                uiThread {
                    pintarTiposMetas()
                    repintarBotonGuardar()
                }
            }
        }

        private fun mapearTipoMeta(it: TipoMeta) =
            TipoMetaModel(it.idTipoMeta, it.descripcion ?: "")
    }

    private fun pintarTiposMetas() {
        view.mostrarTipoMetas(model.descripcionesTiposMeta)
    }
}
