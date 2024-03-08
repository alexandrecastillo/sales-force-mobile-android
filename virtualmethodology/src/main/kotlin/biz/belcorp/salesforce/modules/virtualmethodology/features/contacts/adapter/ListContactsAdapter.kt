package biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.modules.virtualmethodology.R
import biz.belcorp.salesforce.modules.virtualmethodology.features.contacts.model.ListContacts
import kotlinx.android.synthetic.main.item_consultant_contact.view.*

class ListContactsAdapter(
    private val contacts: List<ListContacts> = emptyList(),
    private val onClick: ((ListContacts) -> Unit)? = null
) : RecyclerView.Adapter<ListContactsAdapter.ViewHolder>(), Filterable {

    private var filterContacts = contacts

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_consultant_contact, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filterContacts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterContacts[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filterContacts = filterResults.values as List<ListContacts>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values =
                    if (queryString == null || queryString.isEmpty())
                        contacts
                    else
                        contacts.filter {
                            it.nombre?.toLowerCase()?.contains(queryString) ?: false ||
                                it.phone?.toLowerCase()?.contains(queryString) ?: false
                        }
                return filterResults
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: ListContacts) = with(itemView) {
            val nameContact = itemView.context.getString(R.string.name_contact)
            cboConsultant?.text = String.format(nameContact, model.nombre, model.phone)
            cboConsultant?.setOnClickListener {
                model.checked = !model.checked
                onClick?.invoke(model)
            }
            cboConsultant?.isChecked = model.checked
        }
    }
}
