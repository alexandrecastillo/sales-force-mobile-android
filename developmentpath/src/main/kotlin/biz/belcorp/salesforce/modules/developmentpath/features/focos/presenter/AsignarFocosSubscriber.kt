package biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.AsignarFocosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarView
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel

class AsignarFocosSubscriber(val view: AsignarView?) :
    BaseObserver<AsignarFocosUseCase.Response>() {

    private val mapper = AsignarFocosMapper()

    override fun onComplete() = Unit
    override fun onError(exception: Throwable) = Unit
    override fun onNext(t: AsignarFocosUseCase.Response) {
        doAsync {
            val focos = t.focos.map {
                FocoModel(
                    id = it.elemento.id,
                    descripcion = it.elemento.descripcion,
                    seleccionado = it.seleccionado,
                    focoCodigo = it.elemento.focoCodigo
                )
            }

            val personas = mapper.map(t.personas, t.seleccionHabilitada)

            uiThread {
                configurarTituloFocos(focos, t)
                configurarTituloPersonas(personas)
                view?.cargarFocos(focos)
                view?.cargarPersonas(personas)
                traerFocosEquipo(focos)
                traerCodigoEquipo(personas)
                configurarBotonGuardado(t.esPosibleGuardar)
            }
        }
    }

    private fun configurarTituloPersonas(personas: List<PersonaModel>) {
        if (personas.isEmpty()) {
            view?.ocultarTituloPersonas()
        } else {
            view?.mostrarTituloPersonas()
        }
    }

    private fun configurarTituloFocos(
        focos: List<FocoModel>,
        t: AsignarFocosUseCase.Response
    ) {
        when {
            focos.isEmpty() -> view?.ocultarTituloFocos()
            t.autoAsignacion -> view?.mostrarTituloFocosPropios()
            else -> view?.mostrarTituloFocosHijos()
        }
    }

    private fun configurarBotonGuardado(esPosibleGuardar: Boolean) {
        if (esPosibleGuardar) view?.habilitarBotonGuardado()
        else view?.deshabilitarBotonGuardado()
    }

    private fun traerFocosEquipo(focoSeleccionado: List<FocoModel>) {
        val focos = focoSeleccionado.filter { it.seleccionado }.map { it.focoCodigo }
        val focoSelect = FocoSeleccionado(Constant.CODIGO_UA, 0, focos)
        view?.focoSeleccionado(focoSelect)
    }

    private fun traerCodigoEquipo(codigoPersona: List<PersonaModel>) {
        val codigo = codigoPersona.filter { it.seleccionado }.map { it.codigo }
        val zonaSelect = FocoSeleccionado(Constant.CODIGO_UA, 1, codigo)
        view?.codigoSeleccionado(zonaSelect)
    }

}
