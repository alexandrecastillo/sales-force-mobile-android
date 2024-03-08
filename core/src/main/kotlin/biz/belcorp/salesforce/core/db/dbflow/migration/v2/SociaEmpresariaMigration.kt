package biz.belcorp.salesforce.core.db.dbflow.migration.v2

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.directorio.SociaEmpresariaEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 2, database = AppDatabase::class)
class SociaEmpresariaMigration  : AlterTableMigration<SociaEmpresariaEntity>(SociaEmpresariaEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER,
            EXITOSA
        )
    }

    companion object {
        const val EXITOSA = "Exitosa"
    }
}
