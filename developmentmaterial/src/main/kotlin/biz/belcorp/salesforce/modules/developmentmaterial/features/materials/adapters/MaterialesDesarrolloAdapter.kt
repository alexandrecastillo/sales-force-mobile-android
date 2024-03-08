package biz.belcorp.salesforce.modules.developmentmaterial.features.materials.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.analytics.core.domain.entities.Action
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentmaterial.R
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.model.DocumentModel
import biz.belcorp.salesforce.modules.developmentmaterial.utils.AnalyticUtils
import kotlinx.android.synthetic.main.item_material_desarrollo.view.*


class MaterialesDesarrolloAdapter(private val list: List<DocumentModel>) :
    RecyclerView.Adapter<MaterialesDesarrolloAdapter.ViewHolder>() {

    private var openDocument: ((url: String) -> Unit)? = null
    private var downloadDocument: ((url: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_material_desarrollo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DocumentModel) = with(itemView) {
            tvTitle?.text = item.titulo
            btnOpenPdf?.setOnOneClickListener {
                AnalyticUtils.materialDesarrollo(Action.VIEW, item.titulo)
                openDocument?.invoke(item.url)
            }
            btnDownloadPdf?.setOnOneClickListener {
                AnalyticUtils.materialDesarrollo(Action.DOWNLOAD, item.titulo)
                downloadDocument?.invoke(item.url)
            }
        }

    }

    fun setOpenDocumentListener(listener: (url: String) -> Unit) {
        this.openDocument = listener
    }

    fun setDownloadDocumentListener(listener: (url: String) -> Unit) {
        this.downloadDocument = listener
    }

}
