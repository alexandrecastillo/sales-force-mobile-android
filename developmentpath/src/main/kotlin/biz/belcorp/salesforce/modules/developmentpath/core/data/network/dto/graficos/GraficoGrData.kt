package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos

import com.google.gson.annotations.SerializedName

class GraficoGrData(
    @SerializedName("tipoGrafico")
    val tipoGrafico: Int? = null,
    @SerializedName("titulo")
    val titulo: String? = null,
    @SerializedName("tituloGrafico")
    val tituloGrafico: String? = null,
    @SerializedName("valor")
    val valor: String? = null,
    @SerializedName("subtitulo")
    val subtitulo: String? = null,
    @SerializedName("simboloIzquierda")
    val simboloIzquierda: String? = null,
    @SerializedName("simboloDerecha")
    val simboloDerecha: String? = null,
    @SerializedName("barras")
    val barras: List<ValorBarraData> = emptyList(),
    @SerializedName("topBottom")
    val topsBottoms: List<ValorTopBottomData> = emptyList(),
    @SerializedName("productividad")
    val productivas: List<ProductividadData> = emptyList(),
    @SerializedName("ultimaCampaniaTopBottom")
    val campaniaTopBottom: String?,
    @SerializedName("simboloIzqTopBottom")
    val simboloIzquierdaTopBottom: String? = null,
    @SerializedName("simboloDerTopBottom")
    val simboloDerechaTopBottom: String? = null,
    @SerializedName("mostrarCuadro")
    val mostrarCuadroValores: Boolean = true
)
