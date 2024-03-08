package biz.belcorp.salesforce.modules.consultants.features.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.BuityConsultantViewType
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders.BaseViewHolder
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders.BeautyConsultantViewHolder
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders.GrownConsultantViewHolder
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.holders.PosiblesLevelChangeConsultantViewHolder
import biz.belcorp.salesforce.modules.consultants.features.list.models.ConsultoraModel
import com.google.android.gms.maps.model.LatLng

class BeautyConsultantAdapter(sessionManager: ObtenerSesionUseCase) :
    RecyclerView.Adapter<BaseViewHolder>() {

    private val country = sessionManager.obtener().countryIso
    private var items = mutableListOf<ConsultoraModel>()

    var listener: OnConsultantItemSelected? = null
    var buityConsultantViewType: Int = 0
    var currencySymbol = ""
    var highlightName: String? = null
    var filtroBusqueda: Boolean = false


    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (buityConsultantViewType) {
            BuityConsultantViewType.REGULAR_BEAUTY_CONSULTANT_VIEW -> beautyConsultantVH(parent)
            BuityConsultantViewType.GROWN_BEAUTY_CONSULTANT_VIEW -> grownConsultantVH(parent)
            BuityConsultantViewType.POSIBLES_LEVEL_CHANGES_CONSULTANT_VIEW -> posiblesLevelChangesVH(
                parent
            )
            else -> beautyConsultantVH(parent)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        handleViewHolder(viewHolder, position)
    }

    private fun viewInflater(parent: ViewGroup, layout: Int) =
        LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

    private fun beautyConsultantVH(parent: ViewGroup): BeautyConsultantViewHolder {
        val view = viewInflater(parent, R.layout.item_view_consultant_list)
        return BeautyConsultantViewHolder(view)
    }

    private fun grownConsultantVH(parent: ViewGroup): GrownConsultantViewHolder {
        val view = viewInflater(parent, R.layout.item_view_grown_consultant_list)
        return GrownConsultantViewHolder(view)
    }

    private fun posiblesLevelChangesVH(parent: ViewGroup): PosiblesLevelChangeConsultantViewHolder {
        val view = viewInflater(parent, R.layout.item_view_posible_level_changes_consultan_list)
        return PosiblesLevelChangeConsultantViewHolder(view)
    }

    private fun handleViewHolder(vh: BaseViewHolder, pos: Int) {
        vh.let {
            when (it) {
                is BeautyConsultantViewHolder -> bindBeautyConsultant(it, pos)
                is GrownConsultantViewHolder -> bindGrownConsultant(it, pos)
                is PosiblesLevelChangeConsultantViewHolder -> bindPosiblesConsultant(it, pos)
            }
        }
    }

    private fun bindBeautyConsultant(vh: BeautyConsultantViewHolder, pos: Int) {
        vh.bind(
            filtroBusqueda,
            country,
            currencySymbol,
            highlightName ?: Constants.EMPTY_STRING,
            items[pos],
            listener
        )
    }

    private fun bindGrownConsultant(vh: GrownConsultantViewHolder, pos: Int) {
        vh.bind(items[pos], listener)
    }

    private fun bindPosiblesConsultant(vh: PosiblesLevelChangeConsultantViewHolder, pos: Int) {
        vh.bind(currencySymbol, items[pos], listener)
    }

    fun findAndUpdateConsultant(id: Int, location: LatLng) {
        items.firstOrNull { it.consultorasId == id }?.apply {
            latitud = location.latitude
            longitud = location.longitude
            val index = items.indexOf(this)
            notifyItemChanged(index)
        }
    }

    fun addItems(items: List<ConsultoraModel>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun setListenerAdapter(listener: OnConsultantItemSelected) {
        this.listener = listener
    }

}
