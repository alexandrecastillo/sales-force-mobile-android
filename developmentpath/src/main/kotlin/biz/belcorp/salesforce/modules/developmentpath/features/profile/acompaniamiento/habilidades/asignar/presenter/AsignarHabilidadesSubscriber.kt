package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.presenter

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.people.RegionManager
import biz.belcorp.salesforce.core.domain.entities.people.ZoneManager
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.AsignarHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view.AsignarHabilidadView
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.modules.developmentpath.utils.obtenerPrimerLetra
import biz.belcorp.salesforce.core.utils.uiThread

class AsignarHabilidadesSubscriber(val view: AsignarHabilidadView?) :
    BaseSingleObserver<AsignarHabilidadesUseCase.Response>() {

    override fun onSuccess(t: AsignarHabilidadesUseCase.Response) {
        doAsync {
            val titulo = if (t is AsignarHabilidadesUseCase.ResponseGerenteRegional)
                R.string.rdd_habilidades_subtitulo_gr else R.string.rdd_habilidades_subtitulo_gz
            var gerenteZona: GerenteZonaModel?
            val habilidades = t.habilidades.map {
                HabilidadModel(
                    id = it.elemento.id,
                    comentario = it.elemento.comentario,
                    descripcion = it.elemento.descripcion,
                    seleccionado = it.seleccionado,
                    opacado = !it.seleccionado && t.esMaximoHabilidadesSeleccionado,
                    tipoIcono = it.elemento.tipoIcono,
                    codigo = it.elemento.codigo
                )
            }

            with(t.persona) {
                gerenteZona = GerenteZonaModel(
                    iniciales = iniciales,
                    nombreCompleto = WordUtils.capitalizeFully(nombreApellido),
                    estado = obtenerEstado(this) ?: ""
                )
            }

            uiThread {
                view?.cargarHabilidades(habilidades)
                view?.cargarCantidadesDeHabilidades(
                    titulo,
                    t.numeroSeleccionadas,
                    t.numeroMaximoHabilidades
                )
                gerenteZona?.apply {
                    view?.cargarGerenteZona(this)
                }
                obtenerFocoSeleccionado(t.persona, habilidades)
                if (t.esPosibleGuardar) view?.habilitarBotonGuardado()
                else view?.deshabilitarBotonGuardado()
            }
        }
    }

    private fun obtenerFocoSeleccionado(persona: Persona, habilidades: List<HabilidadModel>) {
        val codigos = habilidades.filter { it.seleccionado }.map { it.codigo }
        val codigoUA = persona.unidadAdministrativa.codigo
        val focoSeleccionado = FocoSeleccionado(codigoUA, 0, codigos)
        view?.cargarFococSeleccionado(focoSeleccionado)

    }

    private fun obtenerEstado(persona: Persona): String? {
        return when (persona) {
            is GerenteRegionRdd -> persona.estadoProductividad
            is GerenteZonaRdd -> persona.estadoProductividad
            else -> throw UnsupportedRoleException(persona.rol)
        }
    }

    companion object {

        private const val SIN_DATOS = "(Sin datos)"
        private const val SD = "SD"

    }

}
