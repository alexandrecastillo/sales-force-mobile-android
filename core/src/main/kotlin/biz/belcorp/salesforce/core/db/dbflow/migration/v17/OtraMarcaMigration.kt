package biz.belcorp.salesforce.core.db.dbflow.migration.v17

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.marcas.OtraMarcaEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 17, database = AppDatabase::class)
class OtraMarcaMigration :
    AlterTableMigration<OtraMarcaEntity>(OtraMarcaEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, CATEGORIA)
        addColumn(SQLiteType.TEXT, CAMPANIA)
    }

    companion object {
        const val CATEGORIA = "categoria"
        const val CAMPANIA  = "campania"
    }
}
