package biz.belcorp.salesforce.modules.postulants.core.domain.enums

enum class UneteTipoDocumento(val tipo: Int, val codigo: String = "") {

    DNI(38, "1"),
    CE(39, "2"),
    PASAPORTE(40, "3"),
    RUC(41, "4"),
    OTROS(42, "5"),
    CI(38),
    EXTRANJERO(39),

    EC_CEDULA(38, "1"),
    EC_RUC(39, "2"),
    EC_PASAPORTE(40, "3"),

    CO_CC(156065, "1"),
    CO_CE(156066, "2"),
    CO_CONTRASENA(156086, "5"),
    CO_PPT(183340, "6"),

    CR_CEDULA_IDENTIDAD(5532, "1"),
    CR_PASAPORTE(5533, "2"),
    CR_RESIDENCIA(5534, "3"),
    CR_CEDULA_RESIDENCIA_TEMPORAL(5535, "4"),
    CR_OTROS(5536, "5"),

    PA_CEDULA_IDENTIDAD(242179, "1"),
    PA_CARNE_PERMISO_TEMPORAL(242180, "2"),
    PA_CARNE_RESIDENTE_PROVISIONAL(242181, "3"),
    PA_CARNE_RESIDENTE_PERMANENTE(242182, "4"),
    PA_PASAPORTE(242183, "5"),
    PA_DOC_VARIOS_OTROS(242184, "9")


}
