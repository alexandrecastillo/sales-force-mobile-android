package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel
import java.util.*

class PlanificadasViewModel(val planificadas: List<MiRutaCardModel>,
                            val eventos: List<EventoModel>,
                            val verAgregarEventos: Boolean,
                            val diaSeleccionado: Date)

class NoPlanificadasViewModel(val noPlanificadas: List<MiRutaCardModel>,
                              val postulantesProactivas: List<MiRutaCardModel>,
                              val postulantesNoProactivas: List<MiRutaCardModel>)
