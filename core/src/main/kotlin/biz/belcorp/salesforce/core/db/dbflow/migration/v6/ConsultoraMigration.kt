package biz.belcorp.salesforce.core.db.dbflow.migration.v6

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 6, database = AppDatabase::class)
class ConsultoraMigration : AlterTableMigration<ConsultoraEntity>(ConsultoraEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, CONSTANCIA_PDV)
        addColumn(SQLiteType.TEXT, NIVEL_PDV)
    }

    companion object {
        const val CONSTANCIA_PDV = "ConstanciaPDV"
        const val NIVEL_PDV = "NivelPDV"
    }

}
