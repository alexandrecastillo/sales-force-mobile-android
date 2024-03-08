package biz.belcorp.salesforce.core.entities.sql.path


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(database = AppDatabase::class, name = "ConfiguracionRDD")
class ConfiguracionRDDEntity : BaseModel() {

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null

    @Column(name = "Parametros")
    @SerializedName("parametros")
    @PrimaryKey
    var parametros: String? = null

    @Column(name = "CantidadVisitasPorDia")
    @SerializedName("cantidadVisitasPorDia")
    var cantidadVisitasDia: Int = 0

    @Column(name = "HoraInicio")
    @SerializedName("horaInicio")
    var horaInicio: Int = 0

    @Column(name = "HoraFin")
    @SerializedName("horaFin")
    var horaFin: Int = 23

    @Column(name = "DuracionVisita")
    @SerializedName("duracionVisita")
    var duracionVisita: Int = 0

    @Column(name = "RadioBusqueda")
    @SerializedName("radioBusqueda")
    var radioBusqueda: Int = 3000

    @Column(name = "DistanciaMin")
    @SerializedName("distanciaMin")
    var distanciaMin: Int = 100

}
