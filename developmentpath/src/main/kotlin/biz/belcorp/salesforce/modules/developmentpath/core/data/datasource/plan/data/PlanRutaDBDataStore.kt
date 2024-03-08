package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data

import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*

class PlanRutaDBDataStore {

    fun obtenerPorId(idPlan: Int): PlanRutaRDDEntity? {
        return (select
            from PlanRutaRDDEntity::class
            where (PlanRutaRDDEntity_Table.ID eq idPlan))
            .querySingle()
    }

    fun actualizarVisitadas(plan: PlanRutaRDDEntity) {
        val update = update<PlanRutaRDDEntity>()
            .set(PlanRutaRDDEntity_Table.TotalVisitadas.eq(plan.totalVisitadas))
            .where((PlanRutaRDDEntity_Table.ID eq plan.id))

        update.execute()
    }
}
