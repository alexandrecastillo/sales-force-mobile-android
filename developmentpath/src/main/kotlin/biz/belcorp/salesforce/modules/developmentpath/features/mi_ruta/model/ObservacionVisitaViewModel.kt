package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

const val OBSERVACION_NO_SELECCIONADA: Int = -1

class ListarObservacionesViewModel(var observaciones: MutableList<ObservacionVisitaModel> = mutableListOf(),
                                   var idSeleccionado: Int = OBSERVACION_NO_SELECCIONADA)

class ObservacionVisitaModel(val id: Int?, val descripcion: String)
