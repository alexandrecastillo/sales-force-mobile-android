package biz.belcorp.salesforce.core.entities.sql.calculator

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "SocialBonus")
class SocialBonusEntity : BaseModel() {

    @Column(name = "CodigoTipoBono")
    @SerializedName("codigoTipoBono")
    @PrimaryKey
    var codigoTipoBono: String? = null

    @Column(name = "DescripcionTipoBono")
    @SerializedName("descripcionTipoBono")
    var descripcionTipoBono: String? = null

    @Column(name = "IndicadorActivo")
    @SerializedName("indicadorActivo")
    var indicadorActivo: String? = null

    @Column(name = "CodigoTipoMedicion")
    @SerializedName("codigoTipoMedicion")
    var codigoTipoMedicion: String? = null

    @Column(name = "IndicadorTipoBono")
    @SerializedName("indicadorTipoBono")
    var indicadorTipoBono: String? = null

    @Column(name = "MontoUnitarioBono")
    @SerializedName("montoUnitarioBono")
    var montoUnitarioBono: Double? = null

    @Column(name = "IngresaCantidad", getterName = "getIngresaCantidad")
    @SerializedName("ingresaCantidad")
    var ingresaCantidad: Boolean = false

    @Column(name = "CodConsulta")
    @SerializedName("codConsulta")
    var codConsulta: String? = null

}
