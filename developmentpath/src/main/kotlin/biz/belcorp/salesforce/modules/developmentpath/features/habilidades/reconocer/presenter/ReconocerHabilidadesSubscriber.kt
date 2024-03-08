package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ReconocerHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view.ReconocerHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel
import biz.belcorp.salesforce.modules.developmentpath.utils.obtenerPrimerLetra

class ReconocerHabilidadesSubscriber(val view: ReconocerHabilidadesView?) :
    BaseSingleObserver<ReconocerHabilidadesUseCase.Response>() {

    override fun onSuccess(t: ReconocerHabilidadesUseCase.Response) {
        doAsync {
            val habilidades = t.habilidades.map {
                HabilidadModel(
                    id = it.elemento.id,
                    descripcion = it.elemento.descripcion,
                    seleccionado = it.seleccionado,
                    esClickeable = t.desactivarSeleccionar,
                    tipoIcono = it.elemento.tipoIcono
                )
            }

            val gerenteZona = GerenteZonaModel(
                iniciales = obtenerIniciales(
                    t.datosPersonaHabilidades.nombre,
                    t.datosPersonaHabilidades.apellido
                ),
                nombreCompleto = WordUtils.capitalizeFully(
                    obtenerNombreCompleto(
                        t.datosPersonaHabilidades.nombre,
                        t.datosPersonaHabilidades.apellido
                    )
                ),
                estado = t.datosPersonaHabilidades.estado
            )

            uiThread {
                view?.cargarHabilidades(habilidades)
                view?.mostrarBotonReconocer(t.mostrarBotonReconocer)
                view?.cargarCantidadesDeHabilidades(
                    t.numeroSeleccionadas,
                    t.numeroMaximoHabilidades
                )
                view?.cargarGerenteZona(gerenteZona)
            }
        }
    }

    private fun obtenerNombreCompleto(nombre: String, apellido: String): String {
        if (nombre.isEmpty() && apellido.isEmpty())
            return "(Sin datos)"

        return "$nombre $apellido".trim()
    }

    private fun obtenerIniciales(nombre: String, apellido: String): String {
        if (nombre.isEmpty() && apellido.isEmpty())
            return "SD"

        return "${nombre.obtenerPrimerLetra()}${apellido.obtenerPrimerLetra()}".trim()
    }
}
