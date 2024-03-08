package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model

sealed class ProductividadOCampaniaModel(val valor: String) {

    class Campania(valor: String) : ProductividadOCampaniaModel(valor)

    class Productiva : ProductividadOCampaniaModel("P")

    class Estable : ProductividadOCampaniaModel("E")

    class Critica : ProductividadOCampaniaModel("C")

    class Ninguno : ProductividadOCampaniaModel("-")

    class Builder {

        companion object {
            const val CODIGO_PRODUCTIVA = "P"
            const val CODIGO_ESTABLE = "E"
            const val CODIGO_CRITICA = "C"
            const val CODIGO_NINGUNO = "-"
        }

        fun construir(codigo: String): ProductividadOCampaniaModel {
            return when (codigo) {
                CODIGO_PRODUCTIVA -> ProductividadOCampaniaModel.Productiva()
                CODIGO_ESTABLE -> ProductividadOCampaniaModel.Estable()
                CODIGO_CRITICA -> ProductividadOCampaniaModel.Critica()
                CODIGO_NINGUNO -> ProductividadOCampaniaModel.Ninguno()
                else -> ProductividadOCampaniaModel.Campania(codigo)
            }
        }
    }
}

