package biz.belcorp.salesforce.core.db.dbflow.migration.v11

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 11, database = AppDatabase::class)
class PostulantMigration :
    AlterTableMigration<PostulanteEntity>(PostulanteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, NOMBRE_MTO)
    }

    companion object {
        const val NOMBRE_MTO = "nombreMto"
    }

}
