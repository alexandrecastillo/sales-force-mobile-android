package biz.belcorp.salesforce.core.entities.sql.comportamientos

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel
import com.raizlabs.android.dbflow.structure.BaseQueryModel

@QueryModel(database = AppDatabase::class, allFields = false)
data class ComportamientoPorcentajeJoinned(

    @Column(name = "Descripcion")
    var descripcion: String? = null,

    @Column(name = "Porcentaje")
    var porcentaje: Int? = null,

    @Column(name = "IconoID")
    var iconoID: Int? = null) : BaseQueryModel()
