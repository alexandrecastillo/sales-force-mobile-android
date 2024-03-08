package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe

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
import kotlinx.android.synthetic.main.unete_pe_paso_5.view.*


@SuppressLint("ViewConstructor")
class UnetePaso5(mContext: Context, view: UnetePaso5View) :
        BaseUnetePaso<UnetePaso5View>(mContext, view), IUnetePaso5,
        UneteDocumentoAdapter.UneteDocumentoListener {

    var documentos: List<UneteDocumentoModel> = emptyList()
    var uneteDocumentoModel: UneteDocumentoModel? = null
    var savedInstanceState: Bundle? = null

    override fun getLayout() = R.layout.unete_pe_paso_5

    override fun createModel(): PostulanteModel? {
        return view.getModel().apply {
            imagenIFE = documentos[0].documento
            imagenReciboOtraMarca = documentos[1].documento
            imagenCDD = documentos[2].documento
            imagenContrato = documentos[3].documento
            imagenPagare = documentos[4].documento
            if (requiereAval == Constant.NUMERO_UNO) {
                imagenDniAval = documentos[5].documento
                imagenReciboPagoAval = documentos[6].documento
            }
            paso = Constant.NUMERO_CINCO
            sincronizado = false
        }
    }

    override fun loadModel() = Unit

    override fun initView() {
        super.initView()
        val adapter = UneteDocumentoAdapter()
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

        val model = view.getModel().apply {
            imagenIFE = documentos[0].documento
            imagenReciboOtraMarca = documentos[1].documento
            imagenCDD = documentos[2].documento
            imagenContrato = documentos[3].documento
            imagenPagare = documentos[4].documento


            if (requiereAval == Constant.NUMERO_UNO) {
                imagenDniAval = documentos[5].documento
                imagenReciboPagoAval = documentos[6].documento
            }
        }
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
