package biz.belcorp.salesforce.core.entities.sql.materialdesarrollo


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "MaterialDesarrollo")
class MaterialDesarrolloEntity(
    @PrimaryKey
    @Column(name = "ID")
    @SerializedName("id")
    var id: Long? = null,

    @Column(name = "NombreDocumento")
    @SerializedName("nombreDocumento")
    var nombreDocumento: String? = null,

    @Column(name = "URL")
    @SerializedName("url")
    var url: String? = null,

    @Column(name = "Rol")
    @SerializedName("rol")
    var rol: String? = null
) : BaseModel()
