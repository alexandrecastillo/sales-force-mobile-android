package biz.belcorp.salesforce.core.db.dbflow.migration.v5

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 5, database = AppDatabase::class)
class ConsultoraMigration : AlterTableMigration<ConsultoraEntity>(ConsultoraEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER, HAS_CASH_PAYMENT)
    }

    companion object {
        const val HAS_CASH_PAYMENT = "HasCashPayment"
    }
}
