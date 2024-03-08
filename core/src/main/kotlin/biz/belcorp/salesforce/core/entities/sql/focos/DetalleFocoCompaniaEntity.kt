package biz.belcorp.salesforce.core.entities.sql.focos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "DetalleFocoCampania")
open class DetalleFocoCompaniaEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "PasoID")
    @SerializedName("pasoID")
    var pasoId: Int = 0

    @Column(name = "TituloPasoID")
    @SerializedName("tituloPasoID")
    var tituloPasoId: Int = 0

    @Column(name = "Nombre")
    @SerializedName("nombre")
    var nombre: String? = null

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

}
