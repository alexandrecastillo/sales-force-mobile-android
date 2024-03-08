package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.cloud.FocosCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.asignacion.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.DetalleFocoSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.ListadoFocosEnAsignacionRepository
import io.reactivex.Completable

class ListadoFocosDataRepository(
    private val listadoFocosDBDataStore: ListadoFocosDBDataStore,
    private val focosSeStore: FocosSeDBDataStore,
    private val focosGzStore: FocosGzDBDataStore,
    private val focosGrStore: FocosGrDBDataStore,
    private val seStore: ListadoSeDBDataStore,
    private val gzStore: ListadoGzDBDataStore,
    private val grStore: ListadoGrDBDataStore,
    private val focosCloudDataStore: FocosCloudDataStore,
    private val mapper: FocoMapper
) : ListadoFocosEnAsignacionRepository {

    override fun obtenerFocos(): List<Foco> {
        return listadoFocosDBDataStore.obtenerFocos()
    }

    override fun obtenerFocosIdSE(personaId: Long): Array<Long> {
        return focosSeStore.obtenerFocosSe(personaId)
    }

    override fun obtenerFocosIdGZ(personaId: Long): Array<Long> {
        return focosGzStore.obtenerFocosGZ(personaId)
    }

    override fun obtenerFocosIdGR(personaId: Long): Array<Long> {
        return focosGrStore.obtenerFocosGr(personaId)
    }

    override fun obtenerFocosPorUA(llaveUA: LlaveUA): Array<Long> {
        return listadoFocosDBDataStore.obtenerFocosPorUa(llaveUA)
    }

    override fun recuperarFocosPara(
        personaId: Long,
        rol: Rol
    ): List<Foco> {
        return listadoFocosDBDataStore.recuperarFocosPara(personaId, rol)
    }

    override fun obtenerSocias(): List<SociaEmpresariaRdd> {
        return seStore.obtenerSocias()
    }

    override fun obtenerGZs(): List<GerenteZonaRdd> {
        return gzStore.obtenerGZs()
    }

    override fun obtenerGRs(): List<GerenteRegionRdd> {
        return grStore.obtenerGRs()
    }

    override fun sincronizar(): Completable {
        return listadoFocosDBDataStore.recuperarPendientes()
            .flatMapCompletable {
                if (it.isNotEmpty())
                    enviarYActualizar(it)
                else
                    Completable.complete()
            }
    }

    private fun enviarYActualizar(focos: List<DetalleFocoSE>): Completable {
        return focosCloudDataStore.subir(mapper.parseToRequest(focos))
            .flatMapCompletable { listadoFocosDBDataStore.marcarComoEnviados() }
    }
}
