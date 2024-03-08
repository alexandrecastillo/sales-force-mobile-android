package biz.belcorp.salesforce.core.entities.sql.indicators

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class)
class IndicadorRutasDesarrolloEntity2 : BaseQueryModel() {

    @Column(name = "TotalPlanificadas")
    @SerializedName("totalPlanificadas")
    var totalPlanificadas: Int = 0

    @Column(name = "TotalVisitadas")
    @SerializedName("totalVisitadas")
    var totalVisitadas: Int = 0
}
