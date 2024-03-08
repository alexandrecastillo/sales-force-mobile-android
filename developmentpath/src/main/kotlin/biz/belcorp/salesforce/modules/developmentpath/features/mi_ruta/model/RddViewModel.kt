package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.mapa.PersonaEnMapaViewModel

class RddViewModel(var cabeceraViewModel: CabeceraViewModel,
                   var calendarioViewModel: CalendarioViewModel,
                   var planificadasViewModel: PlanificadasViewModel,
                   val noPlanificadasViewModel: NoPlanificadasViewModel,
                   var busquedaViewModel: BusquedaViewModel,
                   val personasEnMapaViewModel: PersonaEnMapaViewModel)
