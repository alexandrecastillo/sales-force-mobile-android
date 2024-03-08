package biz.belcorp.salesforce.core.entities.sql.horariovisita

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(
    database = AppDatabase::class,
    name = "HorarioVisitaConsultora",
    useBooleanGetterSetters = false
)
class HorarioVisitaConsultoraEntity : BaseModel() {

    @Column(name = "horarioVisitaId")
    @SerializedName("horarioVisitaId")
    var horarioVisitaId: Int = 0

    @PrimaryKey
    @Column(name = "consultoraId")
    @SerializedName("consultoraId")
    var consultoraId: Long = 0

    @Column(name = "region")
    @SerializedName("region")
    var region: String = Constant.EMPTY_STRING

    @Column(name = "zona")
    @SerializedName("zona")
    var zona: String = Constant.EMPTY_STRING

    @Column(name = "seccion")
    @SerializedName("seccion")
    var seccion: String = Constant.EMPTY_STRING

    @Column(name = "activo")
    @SerializedName("activo")
    var activo: Boolean = false

    @Column(name = "enviado")
    @SerializedName("enviado")
    var enviado: Boolean = false

}
