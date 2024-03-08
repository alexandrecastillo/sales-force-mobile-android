package biz.belcorp.salesforce.core.entities.sql.path

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "IntencionPedido")
class IntencionPedidoDbModel(
        @Column(name = "Id")
        @SerializedName("id")
        var id: Long = 0,

        @Column(name = "Campania")
        @SerializedName("campania")
        @PrimaryKey
        var campania: String? = null,

        @Column(name = "Region")
        @SerializedName("region")
        @PrimaryKey
        var region: String = "",

        @Column(name = "Zona")
        @SerializedName("zona")
        @PrimaryKey
        var zona: String = "",

        @Column(name = "Seccion")
        @SerializedName("seccion")
        @PrimaryKey
        var seccion: String = "",

        @Column(name = "ConsultoraId")
        @SerializedName("consultoraID")
        @PrimaryKey
        var consultoraId: Long = 0,

        @Column(name = "PasaPedido", getterName = "getPasaPedido")
        @SerializedName("pasaPedido")
        var pasaPedido: Boolean = false,

        @Column(name = "Usuario")
        @SerializedName("usuarioCreacion")
        var usuario: String? = null,

        @Column(name = "Enviado", getterName = "getEnviado")
        var enviado: Boolean = true) : BaseModel()
