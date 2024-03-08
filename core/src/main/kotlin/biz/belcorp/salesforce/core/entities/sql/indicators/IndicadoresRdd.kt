package biz.belcorp.salesforce.core.entities.sql.indicators

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class IndicadorRddCount : BaseQueryModel() {
    @Column(name = "cantidad")
    var cantidad: Int = -1
}
