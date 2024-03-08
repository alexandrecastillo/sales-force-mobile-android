package biz.belcorp.salesforce.core.entities.sql.filtros


import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "TiposSaldos")
class TipoSaldoEntity : BaseModel() {

    @Column(name = "IdSaldo")
    @PrimaryKey
    @SerializedName("idSaldo")
    var idSaldo: Int = 0

    @Column(name = "Descripcion")
    @SerializedName("descripcion")
    var descripcion: String? = null

}
