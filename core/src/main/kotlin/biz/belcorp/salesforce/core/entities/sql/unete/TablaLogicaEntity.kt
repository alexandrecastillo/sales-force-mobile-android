package biz.belcorp.salesforce.core.entities.sql.unete

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "ListadoTablaLogicaDatos")
class TablaLogicaEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "TablaLogicaDatosID")
    @SerializedName("tablaLogicaDatosID")
    var id: Int = 0

    @Column(name = "TablaLogicaID")
    @SerializedName("tablaLogicaID")
    var tablaLogicaID: Int = 0

    @Column(name = "Codigo")
    @SerializedName("codigo")
    var codigo: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

}
