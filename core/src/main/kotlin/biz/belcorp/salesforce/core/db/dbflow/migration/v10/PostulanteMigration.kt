package biz.belcorp.salesforce.core.db.dbflow.migration.v10


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 10, database = AppDatabase::class)
class PostulanteMigration : AlterTableMigration<PostulanteEntity>(PostulanteEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.TEXT, IP_DEVICE)
        addColumn(SQLiteType.TEXT, ID_DEVICE)
        addColumn(SQLiteType.TEXT, SOMOBILE_DEVICE)
    }

    companion object {
        const val IP_DEVICE = "ip"
        const val ID_DEVICE = "deviceId"
        const val SOMOBILE_DEVICE = "soMobile"
    }

}
