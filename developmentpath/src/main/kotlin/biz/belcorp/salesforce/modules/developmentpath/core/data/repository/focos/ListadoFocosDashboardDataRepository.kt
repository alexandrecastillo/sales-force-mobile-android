package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos

import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.dashboard.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.RegionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.SeccionMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard.ZonaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.FocosHabilidadesEnDashboardRepository

class ListadoFocosDashboardDataRepository(private val focosStore: FocosDbDataStore,
                                          private val habilidadesStore: HabilidadesDbDataStore,
                                          private val seccionesStore: SeccionesDbDataStore,
                                          private val zonasStore: ZonasDbDataStore,
                                          private val regionesStore: RegionesDbDataStore,
                                          private val seccionMapper: SeccionMapper,
                                          private val zonaMapper: ZonaMapper,
                                          private val regionMapper: RegionMapper
) : FocosHabilidadesEnDashboardRepository {

    override fun obtenerSecciones(): List<SeccionRdd> {
        val modelosSeccion = seccionesStore.obtenerModelosSeccion()
        val modelosFoco = obtenerFocos()
        return modelosSeccion.map { seccionMapper.map(it, modelosFoco) }
    }

    override fun obtenerZonas(): List<ZonaRdd> {
        val modelosZona = zonasStore.obtenerModelosZona()
        val modelosFoco = obtenerFocos()
        val modelosHabilidad = obtenerHabilidades()

        return modelosZona.map { zonaMapper.map(it, modelosFoco, modelosHabilidad) }
    }

    override fun obtenerRegiones(): List<RegionRdd> {
        val modelosRegion = regionesStore.obtenerModelosRegion()
        val modelosFoco = obtenerFocos()
        val modelosHabilidad =obtenerHabilidades()

        return modelosRegion.map { regionMapper.map(it, modelosFoco, modelosHabilidad) }
    }

    private fun obtenerFocos(): List<FocoRddEntity> {
        return focosStore.obtenerModelosFoco()
    }

    private fun obtenerHabilidades() : List<HabilidadEntity> {
        return habilidadesStore.obtenerModelosHabilidad()
    }
}
