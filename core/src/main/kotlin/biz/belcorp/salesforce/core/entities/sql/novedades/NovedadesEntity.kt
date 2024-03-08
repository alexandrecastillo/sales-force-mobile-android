package biz.belcorp.salesforce.core.entities.sql.novedades


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Novedades")
class NovedadesEntity : BaseModel() {

    @Column(name = "Grupo")
    @SerializedName("grupo")
    @PrimaryKey
    var grupo: String? = null

    @Column(name = "Rol")
    @SerializedName("rol")
    @PrimaryKey
    var rol: String? = null

    @Column(name = "Texto")
    @SerializedName("texto")
    @PrimaryKey
    var texto: String? = null

    @Column(name = "Titulo")
    @SerializedName("titulo")
    @PrimaryKey
    var titulo: String? = null

    @Column(name = "TipoArchivo")
    @SerializedName("tipoArchivo")
    @PrimaryKey
    var tipoArchivo: String? = null

    @Column(name = "Url")
    @SerializedName("url")
    @PrimaryKey
    var url: String? = null

    @Column(name = "CampaniaInicio")
    @SerializedName("campaniaInicio")
    @PrimaryKey
    var campaniaInicio: String? = null

    @Column(name = "CampaniaFin")
    @SerializedName("campaniaFin")
    @PrimaryKey
    var campaniaFin: String? = null

}
