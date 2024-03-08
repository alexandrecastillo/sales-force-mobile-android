package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.TipoPago
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.BaseUnetePaso
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso5
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.UnetePaso5View
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.adapter.UneteDocumentoAdapter
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.unete_cl_paso_5.view.*

@SuppressLint("ViewConstructor")
class UnetePaso5(mContext: Context, view: UnetePaso5View) :
    BaseUnetePaso<UnetePaso5View>(mContext, view), IUnetePaso5,
    UneteDocumentoAdapter.UneteDocumentoListener {


    private var documentos: List<UneteDocumentoModel> = emptyList()
    private var uneteDocumentoModel: UneteDocumentoModel? = null

    override fun getLayout() = R.layout.unete_cl_paso_5

    companion object {
        private const val IMAGEN_IFE: Int = Constant.NUMERO_CERO
        private const val IMAGEN_CDD: Int = Constant.NUMERO_UNO
        private const val IMAGEN_PAGARE: Int = Constant.NUMERO_DOS
        private const val IMAGEN_CONTRATO: Int = Constant.NUMERO_TRES
        private const val IMAGEN_CREDITO_AVAL: Int = Constant.NUMERO_CUATRO
        private const val IMAGEN_RECIBO_OTRA_MARCA: Int = Constant.NUMERO_CINCO
    }

    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        model.imagenIFE = documentos[IMAGEN_IFE].documento
        model.imagenCDD = documentos[IMAGEN_CDD].documento
        model.imagenPagare = documentos[IMAGEN_PAGARE].documento
        model.imagenContrato = documentos[IMAGEN_CONTRATO].documento
        model.imagenCreditoAval = documentos[IMAGEN_CREDITO_AVAL].documento
        model.imagenReciboOtraMarca = documentos[IMAGEN_RECIBO_OTRA_MARCA].documento
        model.paso = Constant.NUMERO_CINCO
        model.sincronizado = false

        return model
    }

    override fun loadModel() = Unit

    override fun initView() {
        super.initView()
        val adapter = UneteDocumentoAdapter(R.drawable.icon_pdf_postulant)
        adapter.listener = this
        rwListado?.layoutManager = GridLayoutManager(context, Constant.NUMERO_DOS)
        rwListado?.itemAnimator = DefaultItemAnimator()
        rwListado?.adapter = adapter
    }

    override fun loadDocumentos(documentos: List<UneteDocumentoModel>) {
        this.documentos = documentos
        (rwListado.adapter as UneteDocumentoAdapter).actualizar(this.documentos)
    }

    override fun validate(): Boolean {
        val model = view.getModel()
        return if (model.tipoPago == TipoPago.CONTADO.id) {
            true
        } else {
            documentos.all { it.isValid() }
        }
    }

    override fun onClickDocumento(documentoModel: UneteDocumentoModel) {
        uneteDocumentoModel = documentoModel
        if (!documentoModel.documento.isNullOrEmpty()) {
            view.openZoom(documentoModel.imagenRuta.orEmpty())
        } else {
            view.openDocumentDialog()
        }
    }

    override fun onClickOpcionesDocumento(documentoModel: UneteDocumentoModel) {
        uneteDocumentoModel = documentoModel
        view.openDocumentDialog()
    }

    override fun setDocument(ruta: String, uri: String) {

        uneteDocumentoModel?.documento = uri
        uneteDocumentoModel?.imagenRuta = ruta
        rwListado?.adapter?.notifyDataSetChanged()

        val model = view.getModel()
        model.imagenIFE = documentos[IMAGEN_IFE].documento
        model.imagenCDD = documentos[IMAGEN_CDD].documento
        model.imagenPagare = documentos[IMAGEN_PAGARE].documento
        model.imagenContrato = documentos[IMAGEN_CONTRATO].documento
        model.imagenCreditoAval = documentos[IMAGEN_CREDITO_AVAL].documento
        model.imagenReciboOtraMarca = documentos[IMAGEN_RECIBO_OTRA_MARCA].documento

        view.setModel(model)
    }

    override fun getDisables(enEdicion: Boolean): MutableList<View> {

        val lst = mutableListOf<View>()

        if (!enEdicion) {
            lst.addAll(mutableListOf(rwListado))
        }

        return lst
    }

    override fun showDocuments(show: Boolean) {
        ll_documents_body?.visibility = if (show) View.VISIBLE else View.GONE
        ll_no_documents_needed?.visibility = if (show) View.GONE else View.VISIBLE
    }
}
