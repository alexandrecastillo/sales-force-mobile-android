package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_novedades_incentivos.view.*

class DocumentosAdapter : RecyclerView.Adapter<DocumentosAdapter.ViewHolder>() {

    private val incentivos = mutableListOf<DocumentoModel>()
    var listener: ClickEnArchivoListener? = null

    fun actualizar(data: List<DocumentoModel>) {
        this.incentivos.clear()
        this.incentivos.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_novedades_incentivos)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return incentivos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incentivo = incentivos[position]
        holder.bind(incentivo)
    }

    fun setArchivoListener(listener: ClickEnArchivoListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val abrir: ImageView = itemView.botonAbrirPdf
        private val descargar: ImageView = itemView.botonDescargarPdf
        private val compartir: TextView = itemView.textCompartir
        private val titulo: TextView = itemView.textViewTitulo

        fun bind(modelo: DocumentoModel) {
            titulo.text = modelo.nombreDocumento
            compartir.paintFlags = compartir.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            abrir.setOnOneClickListener {
                listener?.clickEnVerPDF(modelo)
            }
            descargar.setOnOneClickListener {
                listener?.clickEnDescargarPDF(modelo)
            }
            compartir.setOnOneClickListener {
                listener?.clickEnCompartir(modelo)
            }
        }
    }
}
