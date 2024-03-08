package biz.belcorp.salesforce.core.db.dbflow.migration.v18

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.plan.PlanRutaRDDEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 18, database = AppDatabase::class)
class PlanRutaRDDEntityMigration :
    AlterTableMigration<PlanRutaRDDEntity>(PlanRutaRDDEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, DAYS_VISITED)
    }

    companion object {
        const val DAYS_VISITED = "DiasVisitando"
    }
}
