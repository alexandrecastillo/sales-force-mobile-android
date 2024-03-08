package biz.belcorp.salesforce.core.entities.sql.plan

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TablaReportePlanificacion")
class ReporteEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "url_destino")
    @SerializedName("url_destino")
    var urlDestino: String? = null

    @Column(name = "nombre_archivo")
    @SerializedName("nombre_archivo")
    var nombreArchivo: String? = null

    @Column(name = "campania")
    @SerializedName("campania")
    var campania: String? = null

}
