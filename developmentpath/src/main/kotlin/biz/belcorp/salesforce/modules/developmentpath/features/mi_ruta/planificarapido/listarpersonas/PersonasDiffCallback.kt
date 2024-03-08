package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas

import androidx.recyclerview.widget.DiffUtil

class PersonasDiffCallback : DiffUtil.Callback() {

    private var antiguos: List<PersonaEnPlanModel> = emptyList()
    private var nuevos: List<PersonaEnPlanModel> = emptyList()

    fun establecer(antiguos: List<PersonaEnPlanModel>, nuevos: List<PersonaEnPlanModel>) {
        this.antiguos = antiguos
        this.nuevos = nuevos
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            antiguos[oldItemPosition].personaId == nuevos[newItemPosition].personaId

    override fun getOldListSize() = antiguos.size

    override fun getNewListSize() = nuevos.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            antiguos[oldItemPosition].planificada == nuevos[newItemPosition].planificada
}
