package biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.mapper

import biz.belcorp.salesforce.core.entities.sql.calculator.CalculadoraMontoFijoEntity
import biz.belcorp.salesforce.core.entities.sql.calculator.CalculatingResultEntity
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatorResult
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatingResultDetail
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo
import com.google.gson.Gson

class CalculatingResultEntityDataMapper {

    fun parseCalculatingResult(list: List<CalculatingResultEntity>): List<CalculatorResult> {
        return list.map { parseCalculatingResultItem(it) }
    }

    private fun parseCalculatingResultItem(item: CalculatingResultEntity): CalculatorResult {
        return CalculatorResult(
            codResultado = item.codResultado,
            codRegion = item.codRegion,
            codZona = item.codZona,
            codSeccion = item.codSeccion,
            campania = item.campania,
            valorResultado = item.valorResultado?.toDouble(),
            exitoso = item.exitoso,
            bono = item.bono?.toDouble(),
            detalleResultadoCalculadora = createCalculatingResultDetail(item.detalleResultadoCalculadora)
        )
    }

    fun parseCalculatingResult(item: CalculatorResult): CalculatingResultEntity {

        return CalculatingResultEntity().apply {
            codResultado = item.codResultado
            codRegion = item.codRegion
            codZona = item.codZona
            codSeccion = item.codSeccion
            campania = item.campania
            valorResultado = item.valorResultado?.toFloat()
            exitoso = item.exitoso
            bono = item.bono?.toFloat()
            detalleResultadoCalculadora = Gson().toJson(item.detalleResultadoCalculadora)
        }
    }

    private fun createCalculatingResultDetail(data: String): List<CalculatingResultDetail> {
        return createCalculatingResultDetailCustom(data).map { it }
    }

    private fun createCalculatingResultDetailCustom(data: String): List<CalculatingResultDetail> {
        return Gson().fromJson(data, Array<CalculatingResultDetail>::class.java).asList()
    }

    fun parseCalculadoraMontiFijo(entity: CalculadoraMontoFijoEntity): CalculadoraMontoFijo {
        return entity.let {
            CalculadoraMontoFijo(
                codigoRango = it.codigoRango,
                tipoRango = it.tipoRango,
                valorMinimoRango = it.valorMinimoRango,
                valorMaximoRango = it.valorMaximoRango,
                bonoExitosa = it.bonoExitosa,
                numPedidosTotales = it.numPedidosTotales,
                numPedidosMetaCobranzas = it.numPedidosMetaCobranzas,
                promedioVentaRango = it.promedioVentaRango,
                textoBonoExitoso = it.textoBonoExitoso,
                textoInput = it.textoInput,
                mensaje = it.mensaje,
                campaniaProceso = it.campaniaProceso,
                bonoNoExitosa = it.bonoNoExitosa,
                textoBonoNoExitoso = it.textoBonoNoExitoso
            )
        }
    }

    fun parseCalculadoraMontiFijo(list: List<CalculadoraMontoFijoEntity>): List<CalculadoraMontoFijo> {
        val montoFijoList = arrayListOf<CalculadoraMontoFijo>()
        list.forEach {
            montoFijoList.add(parseCalculadoraMontiFijo(it))
        }
        return montoFijoList
    }
}
