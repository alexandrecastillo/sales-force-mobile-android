package biz.belcorp.salesforce.core.db.dbflow.migration.v15


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 15, database = AppDatabase::class)
class DetalleIndicadorUneteMigration :
    AlterTableMigration<DetalleIndicadorUneteEntity>(DetalleIndicadorUneteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER, PROACTIVO_PRE_APROBADOS)
    }

    companion object {
        const val PROACTIVO_PRE_APROBADOS = "ProactivoPreAprobados"
    }

}
