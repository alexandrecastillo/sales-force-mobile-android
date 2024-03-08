package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos

import biz.belcorp.salesforce.core.entities.sql.graficos.ConfiguracionGraficosGrEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GraficoGrMapper {

    private fun parseTopBottom(data: ValorTopBottomData) =
        TopBottom(zona = data.zona, tipo = data.tipo,
            valor = data.valor, porcentaje = data.porcentaje)


    fun parseTituloGrafico(modelo: ConfiguracionGraficosGrEntity) =
        GraficoGr(tipoGrafico = obtenerTipoGraficoPorCodigo(modelo.tipoGrafico),
            titulo = modelo.titulo ?: "-",
            valor = modelo.valor ?: "-")

    private fun parseProductivas(data: List<ProductividadData>): ZonasProductividad {
        val campanias = requireNotNull(data.first().valores.map { it.campania ?: "-" })
        val zonasProductividad = data.map { parse(it) }

        return ZonasProductividad(campanias, zonasProductividad)
    }

    fun parse(data: ProductividadData): ZonasProductividad.ZonaProductividad {
        val valores = data.valores.map { parse(it) }
        val zona = data.zona ?: "-"
        return ZonasProductividad.ZonaProductividad(zona, valores)
    }

    fun parse(productividad: ValoresProductividadData): ZonasProductividad.Productividad {
        return ZonasProductividad.Builder.construir(productividad.valor ?: "-")
    }

    private fun obtenerTipoGraficoPorCodigo(tipoGrafico: Int): TipoGrafico {
        return TipoGrafico.Builder.construir(tipoGrafico)
    }

    fun parseGraficoEspecifico(cadenaDatos: String, tipoGrafico: Int): HistoricoRegion {
        val dataGrafico = extraerGrafico(cadenaDatos, tipoGrafico)
        val campaniasValor = parse(dataGrafico.barras)

        return HistoricoRegion(
            tipoGrafico = TipoGrafico.Builder.construir(tipoGrafico),
            titulo = dataGrafico.titulo ?: "-",
            tituloGrafico = dataGrafico.tituloGrafico ?: "-",
            subtitulo = dataGrafico.subtitulo ?: "",
            simboloADerecha = dataGrafico.simboloDerecha ?: "",
            simboloAIzquierda = dataGrafico.simboloIzquierda ?: "",
            campaniasValoresFdVs = campaniasValor.asReversed(),
            mostrarValoresEnCuadro = dataGrafico.mostrarCuadroValores)

    }

    fun parsetoTopBottom(cadenaDatos: String, tipoGrafico: Int): TopBottomHistorico {
        val dataGrafico = extraerGrafico(cadenaDatos, tipoGrafico)
        return TopBottomHistorico(
            tipoGrafico = TipoGrafico.Builder.construir(tipoGrafico),
            campania = dataGrafico.campaniaTopBottom ?: "",
            top = dataGrafico.extraerTop(),
            bottom = dataGrafico.extraerBottom(),
            simboloAIzquierda = dataGrafico.simboloIzquierdaTopBottom ?: "",
            simboloADerecha = dataGrafico.simboloDerechaTopBottom ?: "",
            mostrarEnDosLineas = TipoGrafico.Builder.construir(tipoGrafico).mostrarEnDosLineas())
    }

    private fun GraficoGrData.extraerTop(): List<TopBottom> {
        return topsBottoms.map { parseTopBottom(it) }
            .filter { it.tipo == biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico.TipoTopBottom.TOP.abreviatura }
    }

    private fun GraficoGrData.extraerBottom(): List<TopBottom> {
        return topsBottoms.map { parseTopBottom(it) }
            .filter { it.tipo == biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico.TipoTopBottom.BOTTOM.abreviatura }
    }

    private fun extraerGrafico(cadenaDatos: String, tipoGrafico: Int): GraficoGrData {
        val gson = Gson()
        val turnsType = object : TypeToken<List<GraficoGrData>>() {}.type
        val graficos = gson.fromJson<List<GraficoGrData>>(cadenaDatos, turnsType)
        return requireNotNull(graficos.first { it.tipoGrafico == tipoGrafico })
    }

    fun parse(barras: List<ValorBarraData>): List<HistoricoRegion.CampaniaValorFdV> {
        return barras.map {
            HistoricoRegion.CampaniaValorFdV(it.campania ?: "-", it.valor?.toFloat() ?: 0f,
                it.porcentaje ?: 0)
        }
    }

    fun parseProductivas(cadenaDatos: String, tipoGrafico: TipoGrafico): ZonasProductividad {
        val dataGrafico = extraerGrafico(cadenaDatos, tipoGrafico.codigo)
        return dataGrafico.extraerProductivas()
    }

    private fun GraficoGrData.extraerProductivas(): ZonasProductividad {
        return parseProductivas(productivas)
    }
}
