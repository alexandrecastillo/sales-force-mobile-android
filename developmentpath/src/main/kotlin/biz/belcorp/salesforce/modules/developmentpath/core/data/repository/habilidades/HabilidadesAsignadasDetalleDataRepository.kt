package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.habilidades

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.HabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.HabilidadesDetalleDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.asignar.HabilidadesAsignaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesAsignadasDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase
import io.reactivex.Single

class HabilidadesAsignadasDetalleDataRepository(
    private val habilidadesDBDataStore: HabilidadesDBDataStore,
    private val habilidadesAsignaDBDataStore: HabilidadesAsignaRepository,
    private val rddPersonaRepository: RddPersonaRepository,
    private val habilidadesDetalleDBDataStore: HabilidadesDetalleDBDataStore
) : HabilidadesAsignadasDetalleRepository {

    override fun obtener(personaId: Long, rol: Rol, campania: String): Single<List<DetalleHabilidadesAcompaniamiento>> {
        return doOnSingle {
            val habilidadesLiderazgo = mutableListOf<DetalleHabilidadesAcompaniamiento>()

            val habilidades = habilidadesDBDataStore.obtener(rol)
            val persona = requireNotNull(rddPersonaRepository.recuperarPersonaPorId(personaId, rol))
            val habilidadesAsignadas = habilidadesAsignaDBDataStore.obtenerPorUA(persona.llaveUA, campania)
            val detalleHabilidades = habilidadesDetalleDBDataStore.obtener()

            habilidadesAsignadas.forEach {
                val habilidad = habilidades.firstOrNull { habilidad -> habilidad.id == it }
                    ?: return@forEach
                val detallesTexto = detalleHabilidades.filter { detalleHabilidad ->
                    detalleHabilidad.habilidadId == it &&
                        detalleHabilidad.tipo == ObtenerHabilidadesDetalleUseCase.DETALLE
                }
                    .map { detalleHabilidad -> detalleHabilidad.text }
                val comentariosTexto = detalleHabilidades.filter { detalleHabilidad ->
                    detalleHabilidad.habilidadId == it &&
                        detalleHabilidad.tipo == ObtenerHabilidadesDetalleUseCase.COMENTARIO
                }
                    .map { detalleHabilidad -> detalleHabilidad.text }
                val detalle = DetalleHabilidadesAcompaniamiento(
                    habilidad = habilidad,
                    comportamientoLista = comentariosTexto,
                    detalleLista = detallesTexto
                )
                habilidadesLiderazgo.add(detalle)
            }
            habilidadesLiderazgo
        }
    }
}
