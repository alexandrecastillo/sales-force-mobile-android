package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.consolidado

import androidx.recyclerview.widget.DiffUtil

class ConsolidadoAcuerdosDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<CumplimientoModel> = emptyList()
    private var nuevos: List<CumplimientoModel> = emptyList()

    fun establecer(antiguos: List<CumplimientoModel>, nuevos: List<CumplimientoModel>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        antiguos[oldItemPosition].campania == nuevos[newItemPosition].campania

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        antiguos[oldItemPosition].estado == nuevos[newItemPosition].estado
}
