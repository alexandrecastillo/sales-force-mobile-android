package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

sealed class NivelModel(var visible: Boolean) {

    class RegresarADashboard(visible: Boolean) : NivelModel(visible)

    open class Ua(val nombreCortoCampania: String,
                  val tipoUa: String,
                  val codigoUa: String,
                  val nombreResponsable: String,
                  val rolLiderAsociado: Rol,
                  visible: Boolean) : NivelModel(visible)

    class UaRegresable(val cantidadPantallasRegreso: Int,
                       codigoCampania: String,
                       tipoUa: String,
                       codigoUa: String,
                       nombreResponsable: String,
                       rolLiderAsociado: Rol,
                       visible: Boolean) : Ua(codigoCampania,
                                              tipoUa,
                                              codigoUa,
                                              nombreResponsable,
                                              rolLiderAsociado,
                                              visible)

    class UaExpandible(val cantidadVerMas: Int,
                       var expandido: Boolean,
                       codigoCampania: String,
                       tipoUa: String,
                       codigoUa: String,
                       nombreResponsable: String,
                       rolLiderAsociado: Rol,
                       visible: Boolean) : Ua(codigoCampania,
                                              tipoUa,
                                              codigoUa,
                                              nombreResponsable,
                                              rolLiderAsociado,
                                              visible)

}
