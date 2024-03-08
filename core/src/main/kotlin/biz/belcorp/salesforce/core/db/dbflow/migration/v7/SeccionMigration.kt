package biz.belcorp.salesforce.core.db.dbflow.migration.v7

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 7, database = AppDatabase::class)
class SeccionMigration :
    AlterTableMigration<SeccionEntity>(SeccionEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, CONSULTORA_CODIGO)
    }

    companion object {
        const val CONSULTORA_CODIGO = "ConsultoraCodigo"
    }

}
