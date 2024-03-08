package biz.belcorp.salesforce.core.entities.sql.comportamientos


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "Comportamiento")
class ComportamientoEntity(
    @Column(name = "Codigo")
    @SerializedName("codigo")
    @PrimaryKey
    var codigo: Long = -1,

    @Column(name = "Comentario")
    @SerializedName("comentario")
    var comentario: String? = null,

    @Column(name = "IconoID")
    @SerializedName("iconoID")
    var iconoId: Int = -1,

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null,

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null,

    @Column(name = "Activo")
    @SerializedName("activo")
    var activo: String? = null
) : BaseModel()
