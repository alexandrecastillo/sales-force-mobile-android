package biz.belcorp.salesforce.core.db.dbflow.migration.v4

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 4, database = AppDatabase::class)
class ConsultoraMigration : AlterTableMigration<ConsultoraEntity>(ConsultoraEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER, SHARE_CATALOG)
    }

    companion object {
        const val SHARE_CATALOG = "ComparteCatalogo"
    }
}
