package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.gz.view.HabilidadAdapter
import kotlinx.android.synthetic.main.item_rdd_dashboard_focos_zona.view.*

class FocosHabilidadesAdapter : RecyclerView.Adapter<FocosHabilidadesAdapter.SeccionFocosViewHolder>() {

    private var secciones: List<FocosYHabilidadesPorUa> = emptyList()

    private var editarFocosListener: EditarFocosListener? = null
    private var asignarHabilidadesListener: AsignarHabilidadesListener? = null

    fun actualizar(secciones: List<FocosYHabilidadesPorUa>) {
        this.secciones = secciones
        notifyDataSetChanged()
    }

    fun updateItem(secciones: List<FocosYHabilidadesPorUa>, position: Int) {
        this.secciones = secciones
        notifyItemChanged(position)
    }

    fun setEditarFocoListener(invocable: (zonaId: Long) -> Unit) {
        editarFocosListener = object : EditarFocosListener {
            override fun alHacerClickEnEditar(zonaId: Long) {
                invocable.invoke(zonaId)
            }
        }
    }

    fun setAsignarHabilidadesListener(invocable: (zonaId: Long, tipoEvevto: Int, position: Int) -> Unit) {
        asignarHabilidadesListener = object : AsignarHabilidadesListener {
            override fun alHacerClickEnAsignar(zonaId: Long, tipoEvento: Int, position: Int) {
                invocable.invoke(zonaId, tipoEvento, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeccionFocosViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_rdd_dashboard_focos_zona,
                         parent, false)

        return SeccionFocosViewHolder(view)
    }

    override fun getItemCount(): Int = secciones.size

    override fun onBindViewHolder(holder: SeccionFocosViewHolder, position: Int) {
        val ua = secciones[position]
        holder.bind(ua, editarFocosListener, asignarHabilidadesListener)
    }

    class SeccionFocosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(ua: FocosYHabilidadesPorUa, editarFocosListener: EditarFocosListener?, asignarHabilidadesListener: AsignarHabilidadesListener?) = with(itemView) {
            tv_titulo?.text = obtenerTitulo(context, ua)
            textCampania?.text = ua.codigoCampania
            ll_editar_focos?.visible(ua.mostrarEditarFocos)
            rv_focos?.visible(ua.mostrarFocos)
            card_sin_focos?.visible(!ua.mostrarFocos)
            ll_asignar_focos?.visible(ua.mostrarAsignarFocos)
            ll_editar_habilidades?.visible(ua.mostrarEditarHabilidades)
            card_sin_habilidades?.visible(!ua.mostrarHabilidades)
            rv_habilidades?.visible(ua.mostrarHabilidades)
            ll_asignar_habilidades?.visible(ua.mostrarAsignarHabilidades)

            ll_editar_focos?.setOnClickListener {
                if (ua.personaId == null) return@setOnClickListener
                editarFocosListener?.alHacerClickEnEditar(ua.personaId)
            }
            ll_asignar_focos?.setOnClickListener {
                if (ua.personaId == null) return@setOnClickListener
                editarFocosListener?.alHacerClickEnEditar(ua.personaId)
            }
            ll_asignar_habilidades?.setOnClickListener {
                if (ua.personaId == null) return@setOnClickListener
                asignarHabilidadesListener?.alHacerClickEnAsignar(ua.personaId, Constant.EVENTO_ASIGNAR_FOCOS, position)
            }

            rv_focos?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()

            rv_focos?.adapter = FocosAdapter()

            rv_habilidades?.layoutManager = NonScrollableLayoutManager()
                .withContext(context)
                .linearVertical()

            rv_habilidades?.adapter = HabilidadAdapter()


            ll_editar_habilidades?.setOnClickListener {
                if (ua.personaId == null) return@setOnClickListener
                asignarHabilidadesListener?.alHacerClickEnAsignar(ua.personaId, Constant.EVENTO_EDITAR_FOCOS, position)
            }
            (rv_focos?.adapter as? FocosAdapter)?.actualizar(ua.focos)
            (rv_habilidades?.adapter as? HabilidadAdapter)?.actualizar(ua.habilidades)
        }

        private fun obtenerTitulo(context: Context, modelo: FocosYHabilidadesPorUa): String {
            return when {
                modelo.ua is ZonaRdd && modelo.coberturada ->
                    context.getString(
                        R.string.rdd_dashboard_tab_focos_titulo_zona_coberturada,
                        modelo.codigoUa,
                        modelo.nombresPersona)
                modelo.ua is ZonaRdd && !modelo.coberturada ->
                    context.getString(
                        R.string.rdd_dashboard_tab_focos_titulo_zona_descoberturada,
                        modelo.codigoUa)
                modelo.ua is RegionRdd && modelo.coberturada ->
                    context.getString(
                        R.string.rdd_dashboard_tab_focos_titulo_region_coberturada,
                        modelo.codigoUa,
                        modelo.nombresPersona)
                modelo.ua is RegionRdd && !modelo.coberturada ->
                    context.getString(
                        R.string.rdd_dashboard_tab_focos_titulo_region_descoberturada,
                        modelo.codigoUa)
                else -> Constant.EMPTY_STRING
            }
        }
    }

    interface EditarFocosListener {
        fun alHacerClickEnEditar(zonaId: Long)
    }

    interface AsignarHabilidadesListener {
        fun alHacerClickEnAsignar(zonaId: Long, tipoevento: Int, position: Int)
    }
}
