package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.adapter

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdoModel

class AcuerdosDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<AcuerdoModel> = emptyList()
    private var nuevos: List<AcuerdoModel> = emptyList()

    fun establecer(antiguos: List<AcuerdoModel>, nuevos: List<AcuerdoModel>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        antiguos[oldItemPosition].id == nuevos[newItemPosition].id

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        antiguos[oldItemPosition].cumplido == nuevos[newItemPosition].cumplido
}
