package biz.belcorp.salesforce.core.db.dbflow.migration.v7

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 7, database = AppDatabase::class)
class SociaEmpresariaMigration :
    AlterTableMigration<SociaEmpresariaEntity>(SociaEmpresariaEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, NIVEL)
    }

    companion object {
        const val NIVEL = "Nivel"
    }

}
