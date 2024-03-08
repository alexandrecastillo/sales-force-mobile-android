package biz.belcorp.salesforce.core.db.dbflow.migration.v8

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculadoraMontoFijoEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 8, database = AppDatabase::class)
class CalculadoraMontoFijoMigration :
    AlterTableMigration<CalculadoraMontoFijoEntity>(CalculadoraMontoFijoEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, TEXTO_BONO_EXITOSA)
    }

    companion object {
        const val TEXTO_BONO_EXITOSA = "TextoBonoExitosa"
    }

}
