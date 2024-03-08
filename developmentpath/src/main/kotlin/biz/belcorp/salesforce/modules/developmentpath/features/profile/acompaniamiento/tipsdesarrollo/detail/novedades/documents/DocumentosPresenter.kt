package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.novedades.Documento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.DocumentoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.ListaDocumentoMapper
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class DocumentosPresenter(
    private val view: DocumentoView,
    private val useCase: DocumentoUseCase,
    private val mapper: ListaDocumentoMapper
) {

    fun obtenerData(params: Params) {
        obtenerIncentivos(params)
    }

    private fun obtenerIncentivos(params: Params) {
        val request = DocumentoUseCase.ParamsDocumentos(
            params.personaId,
            params.personaRol, params.opciones ?: return, DocumentosSubscriber()
        )
        useCase.obtenerDocumentos(request)
    }

    inner class DocumentosSubscriber : BaseSingleObserver<List<Documento>>() {
        override fun onSuccess(t: List<Documento>) {
            doAsync {
                val documentos = mapper.map(t)
                uiThread {
                    mostrarDocumentosEnVista(documentos)
                }
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Logger.loge(TAG, e.localizedMessage)
        }

        private fun mostrarDocumentosEnVista(documentos: List<DocumentoModel>) {
            if (documentos.isNotEmpty()) view.pintarDocumentos(documentos)
            else view.pintarDocumentosVacio()
        }
    }

    companion object {
        private val TAG = DocumentosPresenter::class.java.simpleName
    }
}
