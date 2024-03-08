package biz.belcorp.salesforce.modules.developmentpath.features.resultadovisitas

import androidx.recyclerview.widget.DiffUtil

class ConsultorasDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<ConsultoraModel> = emptyList()
    private var nuevos: List<ConsultoraModel> = emptyList()

    fun establecer(antiguos: List<ConsultoraModel>, nuevos: List<ConsultoraModel>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            antiguos[oldItemPosition].id == nuevos[newItemPosition].id

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return antiguos[oldItemPosition].mostrarPedido == nuevos[newItemPosition].mostrarPedido
    }
}
