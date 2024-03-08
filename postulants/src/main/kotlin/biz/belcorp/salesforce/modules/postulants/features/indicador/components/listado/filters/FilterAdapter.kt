package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.filters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.FiltroAprobadoUneteModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import kotlinx.android.synthetic.main.item_filtro_aprobado.view.*
import java.util.*

class FilterAdapter(var context: Context, list: List<FiltroAprobadoUneteModel>) : BaseAdapter() {

    private var list: MutableList<FiltroAprobadoUneteModel> =
        list as MutableList<FiltroAprobadoUneteModel>

    init {
        this.list = ArrayList()
        this.list.addAll(list)

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val item = view ?: inflateView(context, parent)

        val value =
            if (list[position].description == null) Constant.EMPTY_STRING
            else list[position].description.orEmpty().trim { it <= ' ' }
        item.tv_filtro_aprobado_descripcion.text = value

        return item
    }

    private fun inflateView(context: Context, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.item_filtro_aprobado, parent, false)
    }

}
