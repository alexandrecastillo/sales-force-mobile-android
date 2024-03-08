package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.calculatingresult.mapper

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatorResult
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.CalculatingResultDetail
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultDetailModel
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel

class CalculatingResultMapper {

    fun transformResultado(domain: CalculatorResult?): CalculatingResultModel? {
        var model: CalculatingResultModel? = null
        if (domain != null) {
            model = CalculatingResultModel()
            model.bono = domain.bono
            model.codRegion = domain.codRegion
            model.codResultado = domain.codResultado
            model.codSeccion = domain.codSeccion
            model.codZona = domain.codZona
            model.exitoso = domain.exitoso!!
            model.campania = domain.campania
            model.valorResultado = domain.valorResultado
            model.detalleResultadoCalculadora = transformDetalleResultado(domain.detalleResultadoCalculadora)

        }
        return model

    }

    fun transformResultado(model: CalculatingResultModel?): CalculatorResult? {
        if (model != null) {
            return CalculatorResult(
                valorResultado = model.valorResultado,
                bono = model.bono,
                codRegion = model.codRegion,
                codResultado = model.codResultado,
                codSeccion = model.codSeccion,
                codZona = model.codZona,
                exitoso = model.exitoso,
                campania = model.campania,
                detalleResultadoCalculadora = transformDetalleResultadoReverse(model.detalleResultadoCalculadora)

            )
        }
        return null

    }

    fun transformDetalleResultado(list: List<CalculatingResultDetail>): List<CalculatingResultDetailModel> {
        val result = arrayListOf<CalculatingResultDetailModel>()
        list.forEach { domain ->
            val model = CalculatingResultDetailModel()
            model.cantidad = domain.cantidad
            model.etiqueta = domain.etiqueta
            model.codDetalle = domain.codDetalle
            model.descripcion = domain.descripcion
            model.valor = domain.valor
            model.orden = domain.orden
            result.add(model)
        }
        return result
    }

    fun transformDetalleResultadoReverse(list: List<CalculatingResultDetailModel>): List<CalculatingResultDetail> {
        val result = arrayListOf<CalculatingResultDetail>()
        list.forEach { model ->
            result.add(CalculatingResultDetail(
                cantidad = model.cantidad,
                etiqueta = model.etiqueta,
                codDetalle = model.codDetalle,
                descripcion = model.descripcion,
                valor = model.valor,
                orden = model.orden
            ))
        }
        return result
    }

}
