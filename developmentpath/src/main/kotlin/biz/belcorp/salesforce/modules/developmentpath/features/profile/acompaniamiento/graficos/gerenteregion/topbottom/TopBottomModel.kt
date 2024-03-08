package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom

class TopBottomModel(
    val zona: String,
    val valor: String,
    val porcentaje: String,
    val color: TopBottomColor
) {

    enum class TopBottomColor {
        VERDE, ROJO
    }
}
