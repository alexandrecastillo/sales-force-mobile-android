package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.unete_sv_paso_5.view.*

@SuppressLint("ViewConstructor")
class UnetePaso5(mContext: Context, view: UnetePaso5View) :
        BaseUnetePaso<UnetePaso5View>(mContext, view), IUnetePaso5, UneteDocumentoAdapter.UneteDocumentoListener {

    var documentos: List<UneteDocumentoModel> = emptyList()
    var uneteDocumentoModel: UneteDocumentoModel? = null

    companion object {
        private const val POS_0 = 0
        private const val POS_1 = 1
        private const val POS_2 = 2
        private const val POS_3 = 3
        private const val POS_4 = 4
        private const val POS_5 = 5
        private const val POS_6 = 6
        private const val POS_7 = 7
    }

    var savedInstanceState: Bundle? = null

    override fun getLayout() = R.layout.unete_sv_paso_5


    override fun createModel(): PostulanteModel? {
        val model = view.getModel()

        model.imagenIFE = documentos[POS_0].documento
        model.imagenCDD = documentos[POS_1].documento
        model.imagenReciboPagoAval = documentos[POS_2].documento
        model.imagenPagare = documentos[POS_3].documento
        model.imagenContrato = documentos[POS_4].documento
        model.imagenDniAval = documentos[POS_5].documento
        model.imagenReciboOtraMarca = documentos[POS_6].documento
        model.imagenCreditoAval = documentos[POS_7].documento

        model.paso = Constant.NUMERO_CUATRO
        model.sincronizado = false

        return model
    }

    override fun loadModel() = Unit

    override fun initView() {
        super.initView()
        val adapter =
            UneteDocumentoAdapter()
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
        (rwListado?.adapter)?.notifyDataSetChanged()

        val model = view.getModel()
        model.imagenIFE = documentos[POS_0].documento
        model.imagenCDD = documentos[POS_1].documento
        model.imagenReciboPagoAval = documentos[POS_2].documento
        model.imagenPagare = documentos[POS_3].documento
        model.imagenContrato = documentos[POS_4].documento
        model.imagenDniAval = documentos[POS_5].documento
        model.imagenReciboOtraMarca = documentos[POS_6].documento
        model.imagenCreditoAval = documentos[POS_7].documento

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
