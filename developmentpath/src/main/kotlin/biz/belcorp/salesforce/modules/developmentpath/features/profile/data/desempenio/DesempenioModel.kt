package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio

class DesempenioModel(val campania: String,
                      val descripcionProductividad: String?,
                      val colorProductividad: ColorProductividad) {

    enum class ColorProductividad {
        ROJO, VERDE
    }
}
