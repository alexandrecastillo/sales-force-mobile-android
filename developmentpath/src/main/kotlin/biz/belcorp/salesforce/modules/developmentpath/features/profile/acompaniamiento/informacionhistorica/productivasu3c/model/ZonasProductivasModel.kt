package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model

class ZonaProductivaModel(
    val zona: String,
    val productividadCampaniaUltima: ProductividadOCampaniaModel,
    val productividadCampaniaPenultima: ProductividadOCampaniaModel,
    val productividadCampaniaAntepenultima: ProductividadOCampaniaModel
)
