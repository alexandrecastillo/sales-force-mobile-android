package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.adapter

import androidx.recyclerview.widget.DiffUtil
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.AcuerdosPorCampaniaModel

class AcuerdosPorCampaniaDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<AcuerdosPorCampaniaModel> = emptyList()
    private var nuevos: List<AcuerdosPorCampaniaModel> = emptyList()

    fun establecer(
        antiguos: List<AcuerdosPorCampaniaModel>,
        nuevos: List<AcuerdosPorCampaniaModel>
    ) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        antiguos[oldItemPosition].codigoCampania == nuevos[newItemPosition].codigoCampania

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val acuerdosAntiguos = antiguos[oldItemPosition].acuerdos
        val acuerdosNuevos = nuevos[newItemPosition].acuerdos
        if (acuerdosAntiguos.size != acuerdosNuevos.size) return false
        acuerdosAntiguos.zip(acuerdosNuevos) { antiguo, nuevo ->
            if (antiguo != nuevo) return false
        }
        return true
    }
}
