package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

class CuentaCorrienteModel(
    val colorTipoCargo: ColorTipoCargo,
    val montoOperacion: String,
    val tituloEstadoCuenta: String,
    val descripcionOperacion: String,
    val fechaOperacion: String
) {
    enum class ColorTipoCargo { ROJO, NEGRO }
}
