package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas

data class PersonaEnPlanModel(val personaId: Long,
                              val visitaId: Long?,
                              val iniciales: String,
                              val nombres: String,
                              val descripcion: String,
                              val planificada: Boolean,
                              val fechaPlanificacion: String?)
