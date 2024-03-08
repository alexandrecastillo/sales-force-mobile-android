package biz.belcorp.salesforce.modules.developmentpath.features.focos

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.FocoModel

class FocosDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<FocoModel> = emptyList()
    private var nuevos: List<FocoModel> = emptyList()

    fun establecer(antiguos: List<FocoModel>, nuevos: List<FocoModel>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            antiguos[oldItemPosition].id == nuevos[newItemPosition].id

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            antiguos[oldItemPosition].seleccionado == nuevos[newItemPosition].seleccionado
}
