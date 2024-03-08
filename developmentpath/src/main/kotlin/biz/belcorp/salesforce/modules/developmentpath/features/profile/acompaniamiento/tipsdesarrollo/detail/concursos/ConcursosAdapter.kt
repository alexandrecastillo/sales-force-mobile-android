package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ConcursosAdapter :
    RecyclerView.Adapter<ConcursosAdapter.ConcursoViewHolder>() {

    protected val concursos = arrayListOf<ConcursoViewModel>()

    fun actualizar(concursos: List<ConcursoViewModel>) {
        this.concursos.clear()
        this.concursos.addAll(concursos)
        notifyDataSetChanged()
    }

    override fun getItemCount() = concursos.size

    override fun onBindViewHolder(holder: ConcursoViewHolder, position: Int) {
        holder.bind(concursos[position])
    }

    open class ConcursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(concurso: ConcursoViewModel) = Unit
    }
}
