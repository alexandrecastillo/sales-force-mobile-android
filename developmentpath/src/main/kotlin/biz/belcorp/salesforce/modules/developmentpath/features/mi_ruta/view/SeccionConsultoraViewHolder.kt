package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_mi_ruta.view.*
import kotlinx.android.synthetic.main.layout_cantidad_productos_ppu.view.*

class SeccionConsultoraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val card: CardView? = view.card
    val franja: View? = view.franja
    val ivAvatar: ImageView? = view.iv_avatar
    val tvTituloVisita: TextView? = view.tv_titulo
    val tvHora: TextView? = view.tv_hora
    val llSegmento: LinearLayout? = view.ll_segmento
    val puntoSegmento: View? = view.punto_segmento
    val tvSegmento: TextView? = view.tv_segmento
    val llExito: LinearLayout? = view.ll_exito
    val puntoExito: View? = view.punto_exito
    val tvExito: TextView? = view.tv_exito
    val llSeccion: LinearLayout? = view.ll_seccion
    val tvSeccion: TextView? = view.tv_seccion
    val llSociaNueva: LinearLayout? = view.ll_socia_nueva
    val tvNueva: TextView? = view.tv_nueva
    val imgMap: ImageButton? = view.btn_ic_maps
    val btnMostrarMenu: ImageButton? = view.btn_mostrar_menu
    val separadorMapaMenu: View? = view.separador_mapa_menu
    val ivRealizada: ImageView? = view.iv_rdd_registar
    val llRealizada: LinearLayout? = view.ll_realizada
    val llPlanifica: LinearLayout? = view.ll_planifica
    val tvPlanificar: TextView? = view.tv_planificar
    val includeCantidadProductosPPU: View? = view.includeCantidadProductosPPU
    val tvCantidadProductosPPU: TextView? = view.tvCantidadProductos
    val llTips: LinearLayout? = view.llTips
    val tvTipsDesarrollo: TextView? = view.tvTipsDesarrollo
}
