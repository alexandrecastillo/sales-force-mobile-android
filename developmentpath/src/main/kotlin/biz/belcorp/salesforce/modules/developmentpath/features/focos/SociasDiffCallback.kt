package biz.belcorp.salesforce.modules.developmentpath.features.focos

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel

class SociasDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<PersonaModel> = emptyList()
    private var nuevos: List<PersonaModel> = emptyList()

    fun establecer(antiguos: List<PersonaModel>, nuevos: List<PersonaModel>) {
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
