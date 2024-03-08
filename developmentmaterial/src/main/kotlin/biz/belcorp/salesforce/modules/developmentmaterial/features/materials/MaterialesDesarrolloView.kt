package biz.belcorp.salesforce.modules.developmentmaterial.features.materials

import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.model.DocumentModel

interface MaterialesDesarrolloView {

    fun mostrarCampaniaVenta(nombreCorto: String)

    fun mostrarCampaniaFacturacion(nombreCorto: String)

    fun pintarDocumentos(documentModels: List<DocumentModel>)
}
