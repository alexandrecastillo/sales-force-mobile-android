package biz.belcorp.salesforce.core.db.dbflow.migration.v12

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 12, database = AppDatabase::class)
class PostulantMigration :
    AlterTableMigration<DetalleIndicadorUneteEntity>(DetalleIndicadorUneteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER, PROACTIVO_FINALIZAR)
        addColumn(SQLiteType.INTEGER, PROACTIVO_FINALIZADOS)
    }

    companion object {
        const val PROACTIVO_FINALIZAR = "ProactivoFinalizar"
        const val PROACTIVO_FINALIZADOS = "ProactivoFinalizados"
    }

}
