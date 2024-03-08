package biz.belcorp.salesforce.core.entities.sql.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CalculatingResult")
class CalculatingResultEntity : BaseModel() {

    @Column(name = "IdLocal")
    @PrimaryKey(autoincrement = true)
    @SerializedName("idLocal")
    var idLocal: Long = 0

    @Column(name = "CodResultado")
    @SerializedName("codResultado")
    var codResultado: Int? = null

    @Column(name = "CodRegion")
    @SerializedName("codRegion")
    var codRegion: String? = null

    @Column(name = "CodZona")
    @SerializedName("codZona")
    var codZona: String? = null

    @Column(name = "CodSeccion")
    @SerializedName("codSeccion")
    var codSeccion: String? = null

    @Column(name = "Campania")
    @SerializedName("campania")
    var campania: String? = null

    @Column(name = "ValorResultado")
    @SerializedName("valorResultado")
    var valorResultado: Float? = null

    @Column(getterName = "getExitoso")
    @SerializedName("exitoso")
    var exitoso: Boolean? = null

    @Column(name = "Bono")
    @SerializedName("bono")
    var bono: Float? = null

    @Column(name = "DetalleResultadoCalculadora")
    @SerializedName("detalleResultadoCalculadora")
    var detalleResultadoCalculadora: String = "[]"

}
