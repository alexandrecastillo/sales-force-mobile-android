package biz.belcorp.salesforce.core.entities.sql.horariovisita

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "HorarioVisita", useBooleanGetterSetters = false)
class HorarioVisitaEntity : BaseModel() {

    @PrimaryKey
    @Column(name = "horarioVisitaId")
    @SerializedName("horarioVisitaId")
    var horarioVisitaId: Int = 0

    @Column(name = "descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

    @Column(name = "horaInicio")
    @SerializedName("horaInicio")
    var horaInicio: String? = null

    @Column(name = "horaFin")
    @SerializedName("horaFin")
    var horaFin: String? = null

    @Column(name = "orden")
    @SerializedName("orden")
    var orden: Int = 0

    @Column(name = "otroHorario")
    @SerializedName("otroHorario")
    var otroHorario: Boolean = false

    @Column(name = "activo")
    @SerializedName("activo")
    var activo: Boolean = false

}
