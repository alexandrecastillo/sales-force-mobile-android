package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.termsconditions.R
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.model.TermsConditionsModel
import kotlinx.android.synthetic.main.item_terms_conditions.view.*

class TermsConditionsAdapter(private val list: List<TermsConditionsModel>) :
    RecyclerView.Adapter<TermsConditionsAdapter.ViewHolder>() {

    private var openDocument: ((url: String) -> Unit)? = null
    private var downloadDocument: ((url: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_terms_conditions)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TermsConditionsModel) = with(itemView) {
            tvTitle?.apply {
                text = item.titulo
                setOnOneClickListener { openDocument?.invoke(item.url) }
            }

            btnDownloadPdf?.setOnOneClickListener { downloadDocument?.invoke(item.url) }
        }
    }

    fun setOpenDocumentListener(listener: (url: String) -> Unit) {
        this.openDocument = listener
    }

    fun setDownloadDocumentListener(listener: (url: String) -> Unit) {
        this.downloadDocument = listener
    }

}
