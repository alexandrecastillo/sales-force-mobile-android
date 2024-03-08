package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.utils.imageResource
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.model.AvanceHabilidadModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.item_avance_habilidad_u6c.view.*
import biz.belcorp.salesforce.core.R as coreR

class AvanceHabilidadesAdapter :
    RecyclerView.Adapter<AvanceHabilidadesAdapter.AvanceHabilidadHolder>() {

    private var avanceHabilidades: List<AvanceHabilidadModel> = emptyList()

    fun actualizar(avances: List<AvanceHabilidadModel>) {
        avanceHabilidades = avances
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AvanceHabilidadHolder, position: Int) =
        holder.bind(avanceHabilidades[position])

    override fun getItemCount() = avanceHabilidades.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvanceHabilidadHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_avance_habilidad_u6c, parent, false)

        return AvanceHabilidadHolder(view)
    }

    inner class AvanceHabilidadHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val progressCumplimiento: CircularProgressBar = view.progress_avance_habilidad
        private val imageHabilidad: ImageView = view.image_habilidad_icono
        private val textPorcentaje: TextView = view.text_porcentaje_cumplimiento
        private val textTitulo: TextView = view.text_habilidad_titulo
        private val imageCampaniaX: ImageView = view.image_cumplimiento_campania_x
        private val imageEstrella: ImageView = view.image_estrella_favorito
        private val imageCampaniaXMenos1: ImageView = view.image_cumplimiento_campania_x_menos_1
        private val imageCampaniaXMenos2: ImageView = view.image_cumplimiento_campania_x_menos_2
        private val imageCampaniaXMenos3: ImageView = view.image_cumplimiento_campania_x_menos_3
        private val imageCampaniaXMenos4: ImageView = view.image_cumplimiento_campania_x_menos_4
        private val imageCampaniaXMenos5: ImageView = view.image_cumplimiento_campania_x_menos_5

        fun bind(model: AvanceHabilidadModel) {
            model.apply {
                progressCumplimiento.progress = porcentaje.toFloat()
                dibujarIcono(idIcono)
                escribirPorcentaje(porcentaje)
                mostrarEstrella(estaAsignada)
                textTitulo.text = titulo

                val images = listOf(
                    imageCampaniaX,
                    imageCampaniaXMenos1,
                    imageCampaniaXMenos2,
                    imageCampaniaXMenos3,
                    imageCampaniaXMenos4,
                    imageCampaniaXMenos5)

                val cumplimientos = listOf(
                    cumplimientoCampaniaX,
                    cumplimientoCampaniaXMenos1,
                    cumplimientoCampaniaXMenos2,
                    cumplimientoCampaniaXMenos3,
                    cumplimientoCampaniaXMenos4,
                    cumplimientoCampaniaXMenos5)

                dibujarCumplimiento(images, cumplimientos)
            }
        }

        private fun dibujarIcono(idIcono: Int) {
            imageHabilidad.imageResource = idIcono
            val porterDuffColorFilter =
                PorterDuffColorFilter(recuperarColorIcono(), PorterDuff.Mode.SRC_ATOP)
            imageHabilidad.colorFilter = porterDuffColorFilter
        }

        private fun escribirPorcentaje(porcentaje: Int) {
            val porcentajeString = "$porcentaje %"
            textPorcentaje.text = porcentajeString
        }

        private fun dibujarCumplimiento(imageView: ImageView, cumplimiento: Boolean) {
            if (cumplimiento)
                imageView.imageResource = coreR.drawable.ic_check_green_rdd
        }

        private fun dibujarCumplimiento(images: List<ImageView>, cumplimientos: List<Boolean>) {
            images.forEachIndexed { index, imageView ->
                dibujarCumplimiento(imageView, cumplimientos[index])
            }
        }

        private fun recuperarColorIcono() =
            ContextCompat.getColor(itemView.context, R.color.text_zona_reconocimiento)

        private fun mostrarEstrella(estaAsignada: Boolean) {
            imageEstrella.visibility =
                if (estaAsignada) View.VISIBLE
                else View.INVISIBLE
        }
    }
}
