package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel

interface ExpandirListener {
    fun alClickearExpandir()
}

interface RegresarUnNivelListener {
    fun alClickearRegresarUnNivel(modelo: NivelModel.UaRegresable)
}

interface SalirListener {
    fun alClickearSalir(modelo: NivelModel.Ua)
}

interface RegresarARaizListener {
    fun alClickearRegresarARaiz()
}
