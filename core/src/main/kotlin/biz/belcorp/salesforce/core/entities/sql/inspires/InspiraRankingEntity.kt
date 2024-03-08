package biz.belcorp.salesforce.core.entities.sql.inspires

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "InspiraRanking")
class InspiraRankingEntity  : BaseModel() {

        @Column(name = "Id")
        @PrimaryKey(autoincrement = true)
        @SerializedName("id")
        var id: Long = 0

        @Column(name = "puesto")
        @SerializedName("puesto")
        var puesto: Int = 0

        @Column(name = "usuario")
        @SerializedName("usuario")
        var usuario: String? = null

        @Column(name = "puntaje")
        @SerializedName("puntaje")
        var puntaje: Int = 0

        @Column(name = "flagStatus")
        @SerializedName("flagStatus")
        var flagStatus: String = ""

        @Column(name = "flagEsUsuario")
        @SerializedName("flagEsUsuario")
        var isFlagEsUsuario: Boolean = false

        @Column(name = "bloque")
        @SerializedName("bloque")
        var bloque: Int = 0

}
