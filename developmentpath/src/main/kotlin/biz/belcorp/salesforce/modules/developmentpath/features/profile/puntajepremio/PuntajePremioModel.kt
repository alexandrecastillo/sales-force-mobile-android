package biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio

class PuntajePremioModel(
    val nivel: Int,
    val puntosTotal: Int,
    val puntosObtenidos: Int,
    val puntosRestantes: Int,
    val premio: String,
    val porcentajeAvance: Int,
    val haAlcanzado: Boolean
)
