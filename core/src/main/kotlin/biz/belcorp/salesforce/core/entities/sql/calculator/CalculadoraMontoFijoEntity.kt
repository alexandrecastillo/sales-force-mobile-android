package biz.belcorp.salesforce.core.entities.sql.calculator

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.db.dbflow.AppDatabase
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(database = AppDatabase::class, name = "CalculadoraMontoFijo")
class CalculadoraMontoFijoEntity : BaseModel() {

    @Column(name = "CodigoRango")
    @SerializedName(value = "codigoRango")
    @PrimaryKey
    var codigoRango: String = ""

    @Column(name = "TipoRango")
    @SerializedName(value = "tipoRango")
    var tipoRango: String = ""

    @Column(name = "ValorMinimoRango")
    @SerializedName(value = "valorMinimoRango")
    var valorMinimoRango: Int = 0

    @Column(name = "ValorMaximoRango")
    @SerializedName(value = "valorMaximoRango")
    var valorMaximoRango: Double = 0.0

    @Column(name = "BonoExitosa")
    @SerializedName(value = "bonoExitosa")
    var bonoExitosa: Double = 0.0

    @Column(name = "NumPedidosTotales")
    @SerializedName(value = "numPedidosTotales")
    var numPedidosTotales: Int = 0

    @Column(name = "NumPedidosMetaCobranzas")
    @SerializedName(value = "numPedidosMetaCobranzas")
    var numPedidosMetaCobranzas: Int = 0

    @Column(name = "PromedioVentaRango")
    @SerializedName(value = "promedioVentaRango")
    var promedioVentaRango: Double = 0.0

    @Column(name = "TextoBonoExitosa")
    @SerializedName(value = "textoBonoExitoso")
    var textoBonoExitoso: String = EMPTY_STRING

    @Column(name = "TextoInput")
    @SerializedName(value = "textoInput")
    var textoInput: String = ""

    @Column(name = "Mensaje")
    @SerializedName(value = "mensaje")
    var mensaje: String = ""

    @Column(name = "CampaniaProceso")
    @SerializedName(value = "campaniaProceso")
    var campaniaProceso: String = ""

    @Column(name = "BonoNoExitosa")
    @SerializedName(value = "bonoNoExitosa")
    var bonoNoExitosa: Double = 0.0

    @Column(name = "TextoBonoNoExitoso")
    @SerializedName(value = "textoBonoNoExitoso")
    var textoBonoNoExitoso: String = ""

}
