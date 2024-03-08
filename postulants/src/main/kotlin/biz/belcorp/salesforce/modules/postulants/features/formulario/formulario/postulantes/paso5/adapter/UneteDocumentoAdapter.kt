package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.adapter


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel
import biz.belcorp.salesforce.modules.postulants.utils.cargarImagen
import kotlinx.android.synthetic.main.item_unete_documento.view.*

class UneteDocumentoAdapter(private val customIconError: Int? = null) :
    RecyclerView.Adapter<UneteDocumentoAdapter.UneteDocumentoViewHolder>() {

    var documentos: List<UneteDocumentoModel> = emptyList()
    var listener: UneteDocumentoListener? = null

    fun actualizar(documentos: List<UneteDocumentoModel>) {
        this.documentos = documentos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UneteDocumentoViewHolder, position: Int) {
        val documento = documentos[position]

        var errorDrawable = R.drawable.ic_upload_image

        documento.imagenRuta?.let { img ->
            if (img.trim().isNotEmpty()) {
                customIconError?.let { rsc ->
                    errorDrawable = rsc
                }
            }
        }

        holder.itemView.imgDocumento?.cargarImagen(
            documento.imagenRuta.orEmpty(),
            R.drawable.ic_upload_image,
            errorDrawable
        )

        holder.itemView.txtName?.text = documento.nombre

        holder.itemView.imgDocumento?.setOnClickListener { listener?.onClickDocumento(documento) }
        holder.itemView.imgOpciones?.setOnClickListener {
            listener?.onClickOpcionesDocumento(
                documento
            )
        }
    }

    override fun getItemCount() = documentos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UneteDocumentoViewHolder {
        val view = parent.inflate(R.layout.item_unete_documento)
        return UneteDocumentoViewHolder(view)
    }

    interface UneteDocumentoListener {
        fun onClickDocumento(documentoModel: UneteDocumentoModel)
        fun onClickOpcionesDocumento(documentoModel: UneteDocumentoModel)
    }

    inner class UneteDocumentoViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
