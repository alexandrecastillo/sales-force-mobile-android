package biz.belcorp.salesforce.modules.inspires.model

class InspiraRankingModel(
        var id: Long,
        var puesto: Int,
        var usuario: String?,
        var puntaje: Int,
        var flagStatus: String?,
        var flagEsUsuario: Boolean?,
        var bloque: Int)
