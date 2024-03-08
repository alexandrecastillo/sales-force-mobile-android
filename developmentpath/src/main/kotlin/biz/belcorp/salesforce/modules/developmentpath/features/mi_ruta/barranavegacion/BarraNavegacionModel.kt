package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion

class BarraNavegacionModel(val niveles: List<NivelModel>) {

    val nivelesVisibles get() = niveles.filter { it.visible }
}
