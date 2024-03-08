package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync

import biz.belcorp.salesforce.core.data.repository.auth.SafeApiCallHelper
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitasPorFechaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.DetalleRutaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitasPorFechaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.DetallesRddRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.visitas.CrearVisitaResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import io.reactivex.Completable

class UploadVisitasDataRepository(
    private val visitaDBDataStore: VisitaDBDataStore,
    private val visitasPorFechaDBDataStore: VisitasPorFechaDBDataStore,
    private val syncRestApi: SyncRestApi,
    private val apiCallHelper: SafeApiCallHelper,
    private val mapper: DetalleRutaMapper,
    private val porFechaMapper: VisitasPorFechaMapper
) : UploadVisitasRepository {

    override fun syncUpRegisterVisits(): Completable {
        return enviarActualizacionesPendientes()
            .andThen(enviarVisitasPorFecha())
    }

    override fun syncUpAdditionalVisits(): Completable {
        return enviarInsercionesPendientes()
            .andThen(sendRegisterAdditionalVisits())

    }

    //Envia al servicio las visitas adicionales creadas en la app
    override fun enviarInsercionesPendientes(): Completable {
        return visitaDBDataStore.recuperarInsercionesNoEnviadasSingle()
            .flatMapCompletable { visitas ->
                if (visitas.isNotEmpty()) {
                    enviarYActualizarVisitasCreadas(visitas)
                } else {
                    Completable.complete()
                }
            }
    }

    //Envia las visitas registradas
    private fun enviarYActualizarVisitasCreadas(detalles: List<DetallePlanRutaRDDEntity>): Completable {
        val request = detalles.map { mapper.parseToRequest(it) }

        return apiCallHelper.safeSingleApiCall { syncRestApi.crearVisitas(request) }
            .map {
                asociarIdServerConIdLocal(it)
            }.flatMapCompletable {
                visitaDBDataStore.marcarEnvioDeInsercionesCompletable(it)
            }
    }

    private fun asociarIdServerConIdLocal(it: CrearVisitaResponse) =
        HashMap(
            it.resultado.associateBy({ checkNotNull(it.idLocal) },
                { checkNotNull(it.idServer) })
        )

    override fun sendRegisterAdditionalVisits(): Completable {
        return apiCallHelper.safeSingleApiCall {
            visitaDBDataStore.recuperarActualizacionesSingle()
        }
            .flatMapCompletable {
                if (it.isNotEmpty() && !it.none { detallePlanRutaRDDEntity -> detallePlanRutaRDDEntity.esAdicional }) {
                    val visits = it.filter { detallePlanRutaRDDEntity -> detallePlanRutaRDDEntity.esAdicional }
                    enviarYActualizarVisitas(visits)
                } else
                    Completable.complete()
            }

    }

    override fun enviarActualizacionesPendientes(): Completable {
        return apiCallHelper.safeSingleApiCall {
            visitaDBDataStore.recuperarActualizacionesSingle()
        }
            .flatMapCompletable {
                if (it.isNotEmpty() && !it.none { detallePlanRutaRDDEntity -> !detallePlanRutaRDDEntity.esAdicional }) {
                    val visits = it.filter { detallePlanRutaRDDEntity -> !detallePlanRutaRDDEntity.esAdicional }
                    enviarYActualizarVisitas(visits)
                }else
                    Completable.complete()
            }
    }

    private fun enviarYActualizarVisitas(visitas: List<DetallePlanRutaRDDEntity>): Completable {
        val request = DetallesRddRequest(visitas)

        return apiCallHelper.safeSingleApiCall { syncRestApi.visitas(request) }
            .flatMapCompletable {
                visitaDBDataStore.guardarEnvioDeActualizacionesCompletable()
            }
    }

    override fun enviarVisitasPorFecha(): Completable {
        return visitasPorFechaDBDataStore.obtenerNoEnviadosSingle()
            .flatMapCompletable {
                if (it.isNotEmpty())
                    enviarYActualizarVisitasFecha(it)
                else
                    Completable.complete()
            }
    }

    private fun enviarYActualizarVisitasFecha(visitas: List<VisitasPorFecha>): Completable {
        val request = porFechaMapper.parse(visitas)

        return apiCallHelper.safeSingleApiCall { syncRestApi.visitasCantidad(request) }
            .flatMapCompletable {
                visitasPorFechaDBDataStore.actualizarNoEnviadosCompletable()
            }
    }
}
