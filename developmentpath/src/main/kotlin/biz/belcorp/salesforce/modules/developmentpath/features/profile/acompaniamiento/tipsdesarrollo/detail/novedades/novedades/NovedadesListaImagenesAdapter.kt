package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.mobile.components.core.extensions.inflate
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.TipoArchivoNovedades
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_novedades_compartir_imagenes.view.*


class NovedadesListaImagenesAdapter :
    RecyclerView.Adapter<NovedadesListaImagenesAdapter.ViewHolder>() {

    private val imagenes = mutableListOf<DetalleNovedadesModel>()
    var clickEnNovedadesListener: ClickEnNovedadesListener? = null
    lateinit var tituloCabecera: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_novedades_compartir_imagenes)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagen = imagenes[position]
        holder.bind(imagen)
    }

    override fun getItemCount(): Int = imagenes.size

    fun actualizar(data: List<DetalleNovedadesModel>, tituloCabecera: String) {
        this.tituloCabecera = tituloCabecera
        this.imagenes.clear()
        this.imagenes.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modelo: DetalleNovedadesModel) = with(itemView) {
            val url: String
            when (modelo.tipoArchivo) {
                TipoArchivoNovedades.VIDEO -> {
                    url = context.getString(R.string.url_video_youtube, modelo.idYoutube)
                    iconYoutube?.visibility = View.VISIBLE
                }
                TipoArchivoNovedades.IMAGEN -> {
                    url = modelo.url
                    iconYoutube?.visibility = View.GONE
                }
                else -> url = Constant.EMPTY_STRING
            }

            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
                .error(R.drawable.ic_upload_image)
            Glide.with(itemView.context)
                .load(url)
                .apply(requestOptions)
                .into(imageNovedades)

            clBox?.setOnOneClickListener {
                if (modelo.tipoArchivo == TipoArchivoNovedades.VIDEO) {
                    clickEnNovedadesListener?.abrirVideo(modelo)
                }
            }

            buttonShare?.setOnOneClickListener {
                when (modelo.tipoArchivo) {
                    TipoArchivoNovedades.VIDEO -> {
                        clickEnNovedadesListener?.compartirVideo(modelo.url, tituloCabecera)
                    }
                    TipoArchivoNovedades.IMAGEN -> {
                        val bitmap = imageView2Bitmap(imageNovedades)
                        clickEnNovedadesListener?.compartirImagen(bitmap)
                    }
                    else -> throw Exception("tipo Archivo no soportado")
                }
            }
        }

        private fun imageView2Bitmap(view: ImageView): Bitmap {
            return (view.drawable as BitmapDrawable).bitmap
        }
    }
}
