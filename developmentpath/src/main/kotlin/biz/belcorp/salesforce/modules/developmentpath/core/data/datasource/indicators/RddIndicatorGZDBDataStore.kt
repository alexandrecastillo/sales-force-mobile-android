package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators

import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddVisitasEntityForGz
import biz.belcorp.salesforce.core.entities.sql.indicators.IndicadorRutasDesarrolloEntity2
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import com.raizlabs.android.dbflow.kotlinextensions.and
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class RddIndicatorGZDBDataStore {

    val nonData = "No data"

    private val fromUserRolhierarchyList = listOf(
            Rol.Builder.COD_GERENTE_ZONA,
            Rol.Builder.COD_SOCIA_EMPRESARIA
    )

    val map = hashMapOf<String, IndicadorRutasDesarrolloEntity2?>()


    fun getGzRDDIndicatorData(): Single<IndicadorRddVisitasEntityForGz> {
        return Single.create { s ->
            try {
                val querySingle = getGzRddIndicatorPlanData()

                val entityForGz = parseGzQueryResultToEntity(querySingle)

                s.onSuccess(entityForGz)

            } catch (e: Exception) {
                s.onError(
                    NetworkConnectionException(e.cause ?: Throwable(e.message
                        ?: ""))
                )
            }
        }
    }

    fun getGzRddIndicatorDataBySegment(gzUASegmentId: String): Single<IndicadorRddVisitasEntityForGz> {
        return Single.create { s ->
            try {
                val queryResult = getGzRddIndicatorPlanDataFromGr(gzUASegmentId)

                val entityForGr = parseGzQueryResultToEntity(queryResult)

                s.onSuccess(entityForGr)

            } catch (e: Exception) {
                s.onError(NetworkConnectionException(e.cause ?: Throwable(e.message
                        ?: "")))
            }
        }
    }

    private fun getGzRddIndicatorPlanData(): HashMap<String, IndicadorRutasDesarrolloEntity2?> {
        fromUserRolhierarchyList.forEach {
            map.put(it, querySingleForGz(it))
        }
        return map
    }

    private fun parseGzQueryResultToEntity(queryResult: HashMap<String, IndicadorRutasDesarrolloEntity2?>)
            = IndicadorRddVisitasEntityForGz(
        totalvisitasPlanificadasSEs = queryResult[Rol.Builder.COD_GERENTE_ZONA]?.totalPlanificadas,
        totalvisitasRealizadasSEs = queryResult[Rol.Builder.COD_GERENTE_ZONA]?.totalVisitadas,
        totalvisitasPlanificadasCOs = queryResult[Rol.Builder.COD_SOCIA_EMPRESARIA]?.totalPlanificadas,
        totalvisitasRealizadasCOs = queryResult[Rol.Builder.COD_SOCIA_EMPRESARIA]?.totalVisitadas
    )


    private fun getGzRddIndicatorPlanDataFromGr(uaSegmentId: String)
            : HashMap<String, IndicadorRutasDesarrolloEntity2?> {
        fromUserRolhierarchyList.forEach {
            map.put(it, querySingleForGzFromGr(it, uaSegmentId))
        }
        return map
    }

    private fun querySingleForGz(rol: String): IndicadorRutasDesarrolloEntity2? {
        return (Select(
                Method.sum(PlanRutaRDDEntity_Table.TotalPlanificadas)
                        .`as`("TotalPlanificadas"),
                Method.sum(PlanRutaRDDEntity_Table.TotalVisitadas)
                        .`as`("TotalVisitadas")
        ) from PlanRutaRDDEntity::class
                where (PlanRutaRDDEntity_Table.Rol eq rol))
                .queryCustomSingle(IndicadorRutasDesarrolloEntity2::class.java)
    }

    private fun querySingleForGzFromGr(rol: String, uaSegmentId: String): IndicadorRutasDesarrolloEntity2? {
        return (Select(
                Method.sum(PlanRutaRDDEntity_Table.TotalPlanificadas)
                        .`as`("TotalPlanificadas"),
                Method.sum(PlanRutaRDDEntity_Table.TotalVisitadas)
                        .`as`("TotalVisitadas")
        ) from PlanRutaRDDEntity::class
                where (PlanRutaRDDEntity_Table.Rol eq rol)
                and (PlanRutaRDDEntity_Table.Zona eq uaSegmentId))
                .queryCustomSingle(IndicadorRutasDesarrolloEntity2::class.java)
    }
}
