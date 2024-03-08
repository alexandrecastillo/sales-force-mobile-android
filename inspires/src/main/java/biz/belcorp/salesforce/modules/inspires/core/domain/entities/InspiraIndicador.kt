package biz.belcorp.salesforce.modules.inspires.core.domain.entities

data class InspiraIndicador (val id: Int?,
                             val campania: Int?,
                             val ranking: Int?,
                             val destino: String?,
                             val nivel: String?,
                             val puntaje: String?,
                             val puntajeMax: String?,
                             val nombreSE: String?,
                             val campaniaInicioPrograma: Int?,
                             val campaniaFinPrograma: Int?,
                             val activa: Boolean?,
                             val topeRanking: Int?,
                             val imagenDestino: String?)
