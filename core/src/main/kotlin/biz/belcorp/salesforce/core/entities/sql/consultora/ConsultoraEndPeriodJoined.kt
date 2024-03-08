package biz.belcorp.salesforce.core.entities.sql.consultora

import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.QueryModel

@QueryModel(database = AppDatabase::class)
class ConsultoraEndPeriodJoined {

    @Column(name = "ConsultorasId")
    var consultoraID: Int = 0

    @Column(name = "Nombre")
    var nombre: String? = null

    @Column(name = "TelefonoCasa")
    var telefonoCasa: String? = null

    @Column(name = "TelefonoCelular")
    var telefonoCelular: String? = null

    @Column(name = "codigoConsultora")
    var codigoConsultora: String? = null

    @Column(name = "seccion")
    var seccion: String? = null

    @Column(name = "monto")
    var monto: String? = null

    @Column(name = "nivel")
    var nivelActual: String? = null

    @Column(name = "nivelSiguiente")
    var nivelSiguiente: String? = null

    @Column(name = "DigVerif")
    var digitoVerificador: String? = null

    @Column(name = "Codigo")
    var codigo: String? = null
}
