package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.campania

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data.AcuerdoDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data.HabilidadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.acuerdos.AcuerdoEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania.CampaniaCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.campania.CampaniaACampaniaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.reconocer.HabilidadesReconoceRepository

class CampaniaACampaniaDataRepository(
    private val habilidadesDBDataStore: HabilidadesDBDataStore,
    private val habilidadesReconoceDBDataStore: HabilidadesReconoceRepository,
    private val acuerdoDBDataStore: AcuerdoDBDataStore,
    private val acuerdoEntityDataMapper: AcuerdoEntityDataMapper
) : CampaniaACampaniaRepository {

    companion object {
        const val LIMITE_CAMPANIAS = 3
    }

    private lateinit var campaniaCampaniaList: MutableList<CampaniaCampania>

    override fun obtener(persona: PersonaRdd, campanias: List<Campania>): List<CampaniaCampania> {
        campaniaCampaniaList = mutableListOf()
        require(campanias.size >= LIMITE_CAMPANIAS)

        val campaniasFiltradas = campanias.take(LIMITE_CAMPANIAS)
        val campaniaActual = campanias.first()

        val habilidades = habilidadesDBDataStore.obtener(persona.rol)
        val habilidadesReconocidas = habilidadesReconoceDBDataStore.obtener(persona.id)
        val acuerdos = acuerdoDBDataStore.obtenerAcuerdos(persona.llaveUA)
            .map { acuerdoEntityDataMapper.parsearAcuerdoCampania(it) }

        val limiteHabilidadesSeleccionadas = habilidades.size

        campaniasFiltradas.forEach { campania ->
            val proveedorDeVisibilidad = CampaniaCampania.ProveedorDeVisibilidad()
            val esCampaniaActual = esCampaniaActual(campania.codigo, campaniaActual.codigo)
            proveedorDeVisibilidad.mostrarEditarAcuerdo = esCampaniaActual
            val acuerdosFiltrados = acuerdos.filter { it.campania == campania.codigo }
            var habilidadesReconocidasFiltradas = emptyList<Habilidad>()
            val habilidadesFiltradas = habilidadesReconocidas
                .filter { it.campania == campania.codigo }
                .map { it.habilidades }
                .firstOrNull()
            when {
                habilidadesFiltradas != null -> {
                    habilidadesReconocidasFiltradas = habilidades.filter { habilidadesFiltradas.contains(it.id) }
                    proveedorDeVisibilidad.mostrarConHabilidades = habilidadesReconocidasFiltradas.isNotEmpty()
                    proveedorDeVisibilidad.mostrarSinHabilidades = habilidadesReconocidasFiltradas.isEmpty()
                    proveedorDeVisibilidad.mostrarCardHabilidades = true
                }
                esCampaniaActual -> {
                    proveedorDeVisibilidad.mostrarReconocerHabilidad = true
                }
                else -> {
                    proveedorDeVisibilidad.mostrarSinHabilidades = true
                    proveedorDeVisibilidad.mostrarCardHabilidades = true
                }
            }
            campaniaCampaniaList.add(
                CampaniaCampania(
                    tituloCampania = "Campaña ${campania.obtenerNombreNumerico()}",
                    campania = campania.codigo,
                    habilidades = habilidadesReconocidasFiltradas,
                    porcentaje = habilidadesReconocidasFiltradas.size * 100.0 / limiteHabilidadesSeleccionadas,
                    cantidadHabilidades = "${habilidadesReconocidasFiltradas.size}/$limiteHabilidadesSeleccionadas",
                    proveedorDeVisibilidad = proveedorDeVisibilidad,
                    acuerdos = acuerdosFiltrados)
            )
        }
        return campaniaCampaniaList
    }

    private fun esCampaniaActual(campaniaActual: String?, campania: String): Boolean {
        return campaniaActual == campania
    }
}
