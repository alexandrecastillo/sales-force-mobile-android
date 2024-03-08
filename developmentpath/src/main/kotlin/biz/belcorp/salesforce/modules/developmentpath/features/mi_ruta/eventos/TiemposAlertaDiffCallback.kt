package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

class TiemposAlertaDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<AgregarEventoViewModel.Alerta> = emptyList()
    private var nuevos: List<AgregarEventoViewModel.Alerta> = emptyList()

    fun establecer(antiguos: List<AgregarEventoViewModel.Alerta>,
                   nuevos: List<AgregarEventoViewModel.Alerta>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return antiguos[oldItemPosition].cantidad == nuevos[newItemPosition].cantidad &&
                antiguos[oldItemPosition].unidad == nuevos[newItemPosition].unidad
    }

    override fun getOldListSize() = antiguos.size
    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return antiguos[oldItemPosition].seleccionado == nuevos[newItemPosition].seleccionado
    }
}
