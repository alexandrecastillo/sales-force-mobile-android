package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_novedades_tips_desarrollo.view.*
import biz.belcorp.salesforce.base.R as BaseR

class NovedadesAdapter : RecyclerView.Adapter<NovedadesAdapter.ViewHolder>() {

    private val novedades = mutableListOf<ListadoNovedadesModel>()
    private var listener: ClickEnNovedadesListener? = null
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_novedades_tips_desarrollo)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modelo = novedades[position]
        holder.bind(modelo)
    }

    override fun getItemCount(): Int = novedades.size

    fun actualizar(data: List<ListadoNovedadesModel>) {
        this.novedades.clear()
        this.novedades.addAll(data)
        notifyDataSetChanged()
    }

    fun setNovedadesListener(listener: ClickEnNovedadesListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modelo: ListadoNovedadesModel) = with(itemView) {
            if (modelo.titulo.isEmpty()) textHeader?.visibility = View.GONE
            else textHeader.text = modelo.titulo

            if (modelo.detalleNovedades.isNullOrEmpty()) {
                txtVacio?.visibility = View.VISIBLE
            } else {
                txtVacio?.visibility = View.GONE

                val childAdapter = NovedadesListaImagenesAdapter()
                    .apply {
                        clickEnNovedadesListener = listener
                        actualizar(modelo.detalleNovedades!!, modelo.titulo)
                    }

                val childLayoutManager = LinearLayoutManager(itemView.context)
                childLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                childLayoutManager.initialPrefetchItemCount = 2

                recyclerImagenes?.apply {
                    isNestedScrollingEnabled = false
                    setHasFixedSize(true)
                    addItemDecoration(NovedadesItemDecoration(resources.getDimensionPixelSize(BaseR.dimen.content_inset_small)))
                    layoutManager = childLayoutManager
                    adapter = childAdapter
                    setRecycledViewPool(viewPool)
                }
            }
        }
    }

}
