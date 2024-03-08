package biz.belcorp.salesforce.modules.postulants.features.widget.inputs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import biz.belcorp.salesforce.core.utils.removeExtraWhiteSpaces
import biz.belcorp.salesforce.core.utils.replaceSpecialCharacters
import biz.belcorp.salesforce.modules.postulants.R


class UAutoCompleteAdapter(context: Context, viewResourceId: Int, items: List<String>) :
        ArrayAdapter<String>(context, viewResourceId, items) {

    private var items: List<String>? = null
    private var itemsAll: List<String>? = null
    private var suggestions: MutableList<String>? = null

    init {
        this.items = items
        this.itemsAll = items.toList()
        this.suggestions = mutableListOf()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if (v == null) {
            val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = vi.inflate(R.layout.item_autocomplete, null)
        }
        val customer = items?.get(position)
        val customerNameLabel = v?.findViewById(android.R.id.text1) as TextView
        customerNameLabel.text = customer
        return v
    }


    override fun getFilter(): Filter {
        return nameFilter
    }

    private val nameFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {

            val c = constraint?.toString()
                    ?.replaceSpecialCharacters()
                    ?.removeExtraWhiteSpaces()
                    ?.toLowerCase()

            if (c != null) {
                suggestions?.clear()
                itemsAll?.forEach {
                    val s = it.replaceSpecialCharacters()
                            .removeExtraWhiteSpaces()
                            .toLowerCase()
                    if (s.contains(c)) {
                        suggestions?.add(it)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions?.size ?: 0
                return filterResults
            } else {
                return FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filteredList = results?.values as? List<String>
            filteredList?.takeIf { it.isNotEmpty() }?.also {
                clear()
                addAll(it)
                notifyDataSetChanged()
            }
        }
    }

}
