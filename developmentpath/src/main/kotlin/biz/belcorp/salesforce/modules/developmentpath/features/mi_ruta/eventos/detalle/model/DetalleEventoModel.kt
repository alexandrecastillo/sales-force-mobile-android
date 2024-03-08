package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta

data class DetalleEventoModel(val eventoId: Long,
                              val titulo: String,
                              val ubicacion: String,
                              val fecha: String,
                              val esTodoElDia: Boolean,
                              val horaInicio: String,
                              val horaFin: String,
                              val alerta: Alerta?,
                              val registrado: Boolean,
                              val activo: Boolean)
