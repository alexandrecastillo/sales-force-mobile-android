package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel

class BusquedaViewModel(var secciones: List<Seccion>, val sugerencias: List<String>) {

    class Seccion(val fecha: String = "",
                  val tipoTituloSeccion: TipoTituloSeccion,
                  val personas: List<MiRutaCardModel>)

    enum class TipoTituloSeccion {
        FECHA, NO_PLANIFICADAS, CANDIDATAS_PROACTIVAS, CANDIDATAS_NO_PROACTIVAS
    }
}
