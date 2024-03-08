package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators

import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddVisitasEntityForSe
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

class RddIndicatorSEDBDataStore {

    val nonData = "No data"

    private val fromUserRolhierarchyList = listOf( Rol.Builder.COD_GERENTE_ZONA )

    fun getRDDIndicatorDataForSe(uaSegmentId: String): Single<IndicadorRddVisitasEntityForSe> {
        return Single.create { s ->
            try {
                val querySingle = querySingleForSe(uaSegmentId)

                val entityForSe = parseSeQueryResultToEntity(querySingle)

                s.onSuccess(entityForSe)

            } catch (e: Exception) {
                s.onError(
                    NetworkConnectionException(e.cause ?: Throwable(e.message
                        ?: ""))
                )
            }
        }
    }

    private fun querySingleForSe(uaSegmentId: String): IndicadorRutasDesarrolloEntity2? {
        return (Select(
                Method.sum(PlanRutaRDDEntity_Table.TotalPlanificadas)
                        .`as`("TotalPlanificadas"),
                Method.sum(PlanRutaRDDEntity_Table.TotalVisitadas)
                        .`as`("TotalVisitadas")
        ) from PlanRutaRDDEntity::class
                where (PlanRutaRDDEntity_Table.Rol eq Rol.Builder.COD_SOCIA_EMPRESARIA)
                and (PlanRutaRDDEntity_Table.Seccion eq uaSegmentId))
                .queryCustomSingle(IndicadorRutasDesarrolloEntity2::class.java)
    }

    private fun parseSeQueryResultToEntity(queryResult: IndicadorRutasDesarrolloEntity2?)
            = IndicadorRddVisitasEntityForSe(
        totalvisitasPlanificadasCOs = queryResult?.totalPlanificadas,
        totalvisitasRealizadasCOs = queryResult?.totalVisitadas
    )
}
