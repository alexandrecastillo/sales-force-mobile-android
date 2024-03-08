package biz.belcorp.salesforce.core.db.dbflow.migration.v3


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 3, database = AppDatabase::class)
class PostulanteMigration : AlterTableMigration<PostulanteEntity>(PostulanteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, COLUMNA_FECHA_ENVIO_VALIDAR_POR_GZ)
    }

    companion object {
        const val COLUMNA_FECHA_ENVIO_VALIDAR_POR_GZ = "FechaEnvioValidarPorGZ"
    }

}
