package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

class CabeceraViewModel(val periodo: Campania.Periodo,
                        val nombreCortoCampania: String,
                        val tituloFechaLista: String,
                        val subTituloFechaLista: String,
                        val tituloFechaMapa: String,
                        val subTituloFechaMapa: String,
                        val btnMesAnteriorHabilitado: Boolean,
                        val btnMesSiguienteHabilitado: Boolean,
                        val mostrarMapa: Boolean,
                        val mostrarBotonRetroceder: Boolean)
