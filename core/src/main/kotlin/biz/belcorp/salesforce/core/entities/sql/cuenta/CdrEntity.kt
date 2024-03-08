package biz.belcorp.salesforce.core.entities.sql.cuenta


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CDR")
class CdrEntity : BaseModel() {

    @Column(name = "CdrId")
    @SerializedName("cdrId")
    @PrimaryKey
    var cdrId: Int = 0

    @Column(name = "CodPais")
    @SerializedName("codPais")
    var codPais: String? = null

    @Column(name = "Region")
    @SerializedName("region")
    var region: String? = null

    @Column(name = "Zona")
    @SerializedName("zona")
    var zona: String? = null

    @Column(name = "Seccion")
    @SerializedName("seccion")
    var seccion: String? = null

    @Column(name = "PeriodoProceso")
    @SerializedName("periodoProceso")
    var periodoProceso: String? = null

    @Column(name = "Ncdr")
    @SerializedName("ncdr")
    var ncdr: String? = null

    @Column(name = "CodigoConsultora")
    @SerializedName("codigoConsultora")
    var codigoConsultora: String? = null

    @Column(name = "CampaniaIngreso")
    @SerializedName("campaniaIngreso")
    var campaniaIngreso: String? = null

    @Column(name = "CampaniaReferencia")
    @SerializedName("campaniaReferencia")
    var campaniaReferencia: String? = null

    var cdrProductoEntityList: List<CdrProductoEntity>? = null

}
