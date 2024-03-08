package biz.belcorp.salesforce.modules.inspires.model

class InspireIndicatorModel(
        var id: Int,
        var campania: Int,
        var ranking: Int,
        var destino: String?,
        var nivel: String?,
        var puntaje: String?,
        var puntajeMax: String?,
        var nombreSE: String?,
        var campaniaInicioPrograma: Int,
        var campaniaFinPrograma: Int,
        var activa: Boolean = false,
        var topeRanking: Int = 0,
        var imagenDestino: String?
)
