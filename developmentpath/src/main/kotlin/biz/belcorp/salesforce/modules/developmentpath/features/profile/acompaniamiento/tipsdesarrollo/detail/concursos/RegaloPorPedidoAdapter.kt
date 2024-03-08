package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.modules.developmentpath.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_regalo_por_pedido.view.*

class RegaloPorPedidoAdapter(@LayoutRes private val layout: Int) : ConcursosAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcursoViewHolder {
        return ViewHolder(parent.inflate(layout))
    }

    class ViewHolder(itemView: View) : ConcursosAdapter.ConcursoViewHolder(itemView) {
        override fun bind(concurso: ConcursoViewModel) = with(itemView) {
            Glide.with(context)
                .load(concurso.imagenUrl)
                .into(imgRegalo)

            txtNivelActual?.text = String.format(
                context.getString(R.string.concursos_title_nivel),
                concurso.nivel,
                concurso.puntosNivel
            )
            txtNombreProducto?.text = concurso.descripcionPremio

            if (concurso.nivelAlcanzado()) {
                contenedorProgress?.visibility = View.GONE
                txtProgresoCompleto?.visibility = View.VISIBLE
                txtProgresoCompleto?.text = concurso.descripcionProgreso
            }

            txtPuntos?.text = String.format(
                context.getString(
                    R.string.concursos_puntos_nivel,
                    concurso.puntosNivel
                )
            )
            txtPuntosFaltantes?.text = concurso.descripcionProgreso

            if (concurso.puntosNivel != Constant.NUMBER_ZERO_LONG) {
                pbProgreso?.valorMaximo = concurso.puntosNivel.toInt()
                pbProgreso?.progress = concurso.puntosAcumulados.toInt()
            }
        }
    }
}

