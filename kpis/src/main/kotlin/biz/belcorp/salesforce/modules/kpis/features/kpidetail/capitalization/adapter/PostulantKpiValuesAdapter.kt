package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.inflate
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.GridViewHolder
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.GridModel


internal class PostulantKpiValuesAdapter : RecyclerView.Adapter<GridViewHolder.Default>() {

    private val data = mutableListOf<GridModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GridViewHolder.Default(parent.inflate(R.layout.view_grid_kpi_content))

    override fun getItemCount() = data.size

    fun update(data: List<GridModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GridViewHolder.Default, position: Int) {
        holder.bind(data[position] as DefaultGridModel)
    }
}


