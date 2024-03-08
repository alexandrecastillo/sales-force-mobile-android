package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators

import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddVisitasEntityForGr
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorRutasDesarrolloEntity2
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorVisitasRDDEntity_Table
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class RddIndicatorGRDBDataStore {

    val nonData = "No data"

    private val fromUserRolhierarchyList = listOf(
            Rol.Builder.COD_GERENTE_REGION,
            Rol.Builder.COD_GERENTE_ZONA,
            Rol.Builder.COD_SOCIA_EMPRESARIA
    )

    val map = hashMapOf<String, IndicadorRutasDesarrolloEntity2?>()


    fun getRDDIndicatorDataForGr(): Single<IndicadorRddVisitasEntityForGr> {
        return Single.create { subscriber ->
            try {
                val query = getGrRddIndicatorPlanData()

                val grIndicatorRddEntity = parseGrQueryResultToEntity(query)

                subscriber.onSuccess(grIndicatorRddEntity)

            } catch (e: Exception) {
                subscriber.onError(
                    NetworkConnectionException(e.cause ?: Throwable(e.message
                                                                                   ?: ""))
                )
            }
        }
    }

    fun getRDDIndicatorDataForDv(gzUASegmentId: String): Single<IndicadorRddVisitasEntityForGr> {
        return Single.create { s ->
            try {
                val queryResult = getGrRddIndicatorPlanDataFromDv(gzUASegmentId)

                val entityForGr = parseGrQueryResultToEntity(queryResult)

                s.onSuccess(entityForGr)

            } catch (e: Exception) {
                s.onError(NetworkConnectionException(e.cause ?: Throwable(e.message
                                                                          ?: "")))
            }
        }
    }

    fun getRDDIndicatorDataForGrAsDv(gzUASegmentId: String): Single<IndicadorVisitasRDDEntity> {
        return Single.create { emitter ->

            val query = (select from IndicadorVisitasRDDEntity::class
                    where (IndicadorVisitasRDDEntity_Table.Region.withTable()
                    eq gzUASegmentId.toUpperCase())
                    and (IndicadorVisitasRDDEntity_Table.Zona.withTable() eq "-")
                    and (IndicadorVisitasRDDEntity_Table.Seccion.withTable() eq "-"))

            val modelo = requireNotNull(query.querySingle()) {
                "Error al recuperar indicador de visitas"
            }

            emitter.onSuccess(modelo)
        }
    }

    private fun parseGrQueryResultToEntity(queryResult: HashMap<String, IndicadorRutasDesarrolloEntity2?>) =
        IndicadorRddVisitasEntityForGr(
            totalVisitasPlanificadasGZs = queryResult[Rol.Builder.COD_GERENTE_REGION]?.totalPlanificadas,
            totalVisitasRealizadasGZs = queryResult[Rol.Builder.COD_GERENTE_REGION]?.totalVisitadas,
            totalvisitasPlanificadasSEs = queryResult[Rol.Builder.COD_GERENTE_ZONA]?.totalPlanificadas,
            totalvisitasRealizadasSEs = queryResult[Rol.Builder.COD_GERENTE_ZONA]?.totalVisitadas,
            totalvisitasPlanificadasCOs = queryResult[Rol.Builder.COD_SOCIA_EMPRESARIA]?.totalPlanificadas,
            totalvisitasRealizadasCOs = queryResult[Rol.Builder.COD_SOCIA_EMPRESARIA]?.totalVisitadas
        )

    private fun getGrRddIndicatorPlanData(): HashMap<String, IndicadorRutasDesarrolloEntity2?> {
        fromUserRolhierarchyList.forEach {
            map.put(it, querySingleForGr(it))
        }
        return map
    }

    private fun getGrRddIndicatorPlanDataFromDv(uaSegmentId: String)
            : HashMap<String, IndicadorRutasDesarrolloEntity2?> {
        fromUserRolhierarchyList.forEach {
            map.put(it, querySingleForGzFromDv(it, uaSegmentId))
        }
        return map
    }

    private fun querySingleForGr(rol: String): IndicadorRutasDesarrolloEntity2? {
        return (Select(
                Method.sum(PlanRutaRDDEntity_Table.TotalPlanificadas)
                        .`as`("TotalPlanificadas"),
                Method.sum(PlanRutaRDDEntity_Table.TotalVisitadas)
                        .`as`("TotalVisitadas")
        ) from PlanRutaRDDEntity::class
                where (PlanRutaRDDEntity_Table.Rol eq rol))
                .queryCustomSingle(IndicadorRutasDesarrolloEntity2::class.java)
    }

    private fun querySingleForGzFromDv(rol: String,
                                       uaSegmentId: String): IndicadorRutasDesarrolloEntity2? {
        return (Select(
                Method.sum(PlanRutaRDDEntity_Table.TotalPlanificadas)
                        .`as`("TotalPlanificadas"),
                Method.sum(PlanRutaRDDEntity_Table.TotalVisitadas)
                        .`as`("TotalVisitadas")
        ) from PlanRutaRDDEntity::class
                where (PlanRutaRDDEntity_Table.Rol eq rol)
                and (PlanRutaRDDEntity_Table.Region eq uaSegmentId))
                .queryCustomSingle(IndicadorRutasDesarrolloEntity2::class.java)
    }
}
