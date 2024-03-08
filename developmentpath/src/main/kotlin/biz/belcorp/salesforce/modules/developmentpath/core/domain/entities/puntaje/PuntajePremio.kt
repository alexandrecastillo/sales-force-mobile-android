package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.puntaje

class PuntajePremio(val nivel: Int,
                    val puntosMeta: Int,
                    val puntosObtenidos: Int,
                    val premio: String) {

    val puntosRestantes get() = puntosMeta - puntosObtenidos

    val haAlcanzadoMeta get() = puntosObtenidos >= puntosMeta

    val existeMeta get() = puntosMeta != 0

    val porcentaje get() =
        if (puntosMeta != 0)
            ((puntosObtenidos.toDouble() / puntosMeta.toDouble()) * 100).toInt()
        else
            0

    data class Request(val isoPais: String,
                       var campania: String = "",
                       var codigoConsultora: String,
                       val idConsultora: String)
}
