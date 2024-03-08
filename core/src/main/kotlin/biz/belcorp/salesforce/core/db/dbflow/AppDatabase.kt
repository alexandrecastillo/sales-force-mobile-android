package biz.belcorp.salesforce.core.db.dbflow

import com.raizlabs.android.dbflow.annotation.ConflictAction
import com.raizlabs.android.dbflow.annotation.Database


@Database(
    name = AppDatabase.NAME,
    version = AppDatabase.VERSION,
    insertConflict = ConflictAction.IGNORE,
    updateConflict = ConflictAction.REPLACE
)
object AppDatabase {

    const val NAME = "salesforce"
    const val VERSION = 18

}
