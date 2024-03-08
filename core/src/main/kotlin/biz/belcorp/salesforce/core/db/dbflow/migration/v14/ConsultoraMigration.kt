package biz.belcorp.salesforce.core.db.dbflow.migration.v14

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 14, database = AppDatabase::class)
class ConsultoraMigration : AlterTableMigration<ConsultoraEntity>(ConsultoraEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, DIGITAL_SEGMENT)
        addColumn(SQLiteType.TEXT, SUGGESTED_MESSAGE)
    }

    companion object {
        const val DIGITAL_SEGMENT = "DigitalSegment"
        const val SUGGESTED_MESSAGE = "MensajeSugerido"
    }
}
