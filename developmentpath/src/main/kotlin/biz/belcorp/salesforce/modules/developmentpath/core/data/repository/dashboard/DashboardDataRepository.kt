package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dashboard

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.cloud.DashboardCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.data.DashboardLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.dashboard.DashboardResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.DashboardRepository
import io.reactivex.Completable

class DashboardDataRepository(
    val database: DashboardLocalDataStore,
    private val cloud: DashboardCloudDataStore
) : DashboardRepository {


    override fun sincronizar(request: DashboardRepository.Request): Completable {
        return cloud.obtener(request).flatMapCompletable { response ->
            val resultado = validarResultado(response)

            return@flatMapCompletable database.crearOActualizarPlanRuta(resultado.planRutaRDD)
                .andThen(database.crearOActualizarDirectorio(resultado.directorioVentaUsuario))
                .andThen(database.crearOActualizarZonas(resultado.zonas))
                .andThen(database.crearOActualizarSecciones(resultado.secciones))
                .andThen(database.crearOActualizarCampanias(resultado.campaniasUa))
                .andThen(database.crearOActualizarPuntajeReconocimientos(resultado.puntajeReconocimientos))
                .andThen(database.crearOActualizarDesarrolloHabilidades(resultado.desarrolloHabilidadesRDD))
                .andThen(database.crearOActualizarComportamientoDetallePorcentaje(resultado.comportamientoDetallePorcentaje))
        }
    }

    private fun validarResultado(response: DashboardResponse): DashboardResponse.Resultado {
        return requireNotNull(response.resultado) { "Formato de respuesta inválido" }
    }
}
