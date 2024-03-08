package biz.belcorp.salesforce.core.db.dbflow.migration.v16

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 16, database = AppDatabase::class)
class PostulanteLinkMigration :
    AlterTableMigration<PostulanteEntity>(PostulanteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, POSTULANTE_LINK)
    }

    companion object {
        const val POSTULANTE_LINK = "Link"
    }

}
