package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.parseToShortString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cuenta.CuentaCorriente

class EstadoCuentaModelMapper {

    fun parse(entity: CuentaCorriente) = CuentaCorrienteModel(
        colorTipoCargo = obtenerColorTipoCargo(entity.tipoCargoAbono),
        montoOperacion = entity.montoOperacion ?: "0.0",
        descripcionOperacion = entity.descripcionOperacion ?: "-",
        tituloEstadoCuenta = obtenerTituloEstadoCuenta(entity.tipoCargoAbono),
        fechaOperacion = entity.fechaOperacion?.parseToShortString() ?: "-"
    )

    private fun obtenerTituloEstadoCuenta(tipoCargoAbono: String?): String {
        return if (tipoCargoAbono != null && tipoCargoAbono.trim() == Constant.DEBE) "CARGO" else "ABONO"
    }

    private fun obtenerColorTipoCargo(tipoCargoAbono: String?): CuentaCorrienteModel.ColorTipoCargo {
        return if (tipoCargoAbono != null && tipoCargoAbono.trim() == Constant.DEBE) CuentaCorrienteModel.ColorTipoCargo.ROJO
        else CuentaCorrienteModel.ColorTipoCargo.NEGRO
    }
}
