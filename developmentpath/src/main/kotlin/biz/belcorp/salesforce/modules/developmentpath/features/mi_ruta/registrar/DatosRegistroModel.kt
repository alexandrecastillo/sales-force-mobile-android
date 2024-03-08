package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

import biz.belcorp.salesforce.core.entities.zonificacion.Rol

data class DatosRegistroModel(var mostrarAcuerdos: Boolean,
                              var mostrarSwitch: Boolean,
                              var nombrePersona: String,
                              var switchHabilitado: Boolean,
                              var rolVisita: Rol?) {
    companion object {
        fun inicializar(): DatosRegistroModel {
            return DatosRegistroModel(mostrarAcuerdos = false,
                                      mostrarSwitch = false,
                                      nombrePersona = "",
                                      switchHabilitado = false,
                                      rolVisita = null)
        }
    }
}
