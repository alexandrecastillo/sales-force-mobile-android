package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.CampaniaCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.campania.ObtenerCampaniaACampaniaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class ObtenerCampaniaCampaniaSubscriber(val view: CampaniaCampaniaView?) : BaseSingleObserver<ObtenerCampaniaACampaniaUseCase.Response>() {

    override fun onError(e: Throwable) = e.printStackTrace()

    override fun onSuccess(t: ObtenerCampaniaACampaniaUseCase.Response) {
        doAsync {
            val campaniaCampanias = t.campaniaACampaniaList.map {
                CampaniaCampaniaModel(
                    campania = it.campania,
                    tituloCampania = it.tituloCampania,
                    cantidadHabilidadesReconocidas = it.cantidadHabilidades,
                    mostrarReconocerHabilidades = it.proveedorDeVisibilidad.mostrarReconocerHabilidad,
                    mostrarSinHabilidades = it.proveedorDeVisibilidad.mostrarSinHabilidades,
                    mostrarHabilidades = it.proveedorDeVisibilidad.mostrarConHabilidades,
                    mostrarCardHabilidades = it.proveedorDeVisibilidad.mostrarCardHabilidades,
                    porcentaje = it.porcentaje,
                    acuerdos = parsearAcuerdos(it),
                    habilidadesReconocidas = it.habilidades.map { habilidadReconocida ->
                        HabilidadModel(
                            id = habilidadReconocida.id,
                            tipoIconoId = habilidadReconocida.tipoIcono)
                    })
            }

            uiThread {
                view?.cargar(campaniaCampanias)
            }
        }
    }

    private fun parsearAcuerdos(it: CampaniaCampania): List<AcuerdoModel> {
        val modelos = it.acuerdos.map { acuerdo ->
            AcuerdoModel(id = acuerdo.id,
                descripcion = acuerdo.contenido,
                mostrarEliminarAcuerdo = it.proveedorDeVisibilidad.mostrarEditarAcuerdo,
                mostrarEditarAcuerdo = it.proveedorDeVisibilidad.mostrarEditarAcuerdo,
                mostrarTextoAcuerdo = it.proveedorDeVisibilidad.mostrarEditarAcuerdo,
                mostrarCancelar = false,
                mostrarEdicionTextoAcuerdo = false,
                mostrarFechaAcuerdo = true,
                mostrarGuardarAcuerdo = false,
                fecha = acuerdo.fecha.toDescriptionDay().orEmpty(),
                mostrarLineaInferior = true)
        }

        modelos.lastOrNull()?.mostrarLineaInferior = false

        return modelos
    }
}
