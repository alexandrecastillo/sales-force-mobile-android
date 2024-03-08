package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.item_programa_brillante.view.*

class ProgramaBrillanteAdapter(@LayoutRes private val layout: Int) : ConcursosAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcursoViewHolder {
        return ViewHolder(parent.inflate(layout))
    }

    class ViewHolder(itemView: View) : ConcursoViewHolder(itemView) {
        override fun bind(concurso: ConcursoViewModel) = with(itemView) {
            if (concurso.perteneceCaminoBrillante()) {
                txtNivelActual?.text = String.format(context.getString(R.string.concursos_brillante_nivel), concurso.nivel)
                pbProgreso?.thumb = concurso.iconoProgreso
            } else {
                txtNivelActual?.visibility = View.GONE
                pbProgreso?.thumb = R.drawable.group_icon_bonification
            }

            txtPuntosAcumulados?.text = String.format(context.getString(R.string.concursos_brillante_puntos), concurso.puntosAcumulados)
            pbProgreso?.valorMaximo = concurso.puntosNivel.toInt()
            pbProgreso?.progress = concurso.puntosAcumulados.toInt()

            txtPuntosFaltantes?.text = concurso.descripcionProgreso
        }
    }
}
