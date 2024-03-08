package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.header_seccion_rdd.view.*

class SeccionConsultoraHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivHeader: ImageView = view.iv_header
    val tvHeader: TextView = view.tv_header
}
