package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel

interface IUnetePaso5 : IUnetePaso {
    fun loadDocumentos(documentos: List<UneteDocumentoModel>) = Unit
    fun setDocument(uri: String) = Unit
    fun setDocument(ruta: String, uri: String) = Unit
    fun showDocuments(show : Boolean) = Unit
}
