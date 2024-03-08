package biz.belcorp.salesforce.core.db.dbflow.migration.v13

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import biz.belcorp.salesforce.core.entities.configuration.ConfigurationCountryEntity
import biz.belcorp.salesforce.core.entities.sql.unete.detalle.DetalleIndicadorUneteEntity
import com.raizlabs.android.dbflow.annotation.Migration
import com.raizlabs.android.dbflow.sql.SQLiteType
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration

@Migration(version = 13, database = AppDatabase::class)
class ConfigurationCountryMigration :
    AlterTableMigration<ConfigurationCountryEntity>(ConfigurationCountryEntity::class.java) {

    override fun onPreMigrate() {
        super.onPreMigrate()
        addColumn(SQLiteType.INTEGER, FLAG_SHOW_PROACTIVE)
    }

    companion object {
        const val FLAG_SHOW_PROACTIVE = "flagShowProactive"
    }

}
