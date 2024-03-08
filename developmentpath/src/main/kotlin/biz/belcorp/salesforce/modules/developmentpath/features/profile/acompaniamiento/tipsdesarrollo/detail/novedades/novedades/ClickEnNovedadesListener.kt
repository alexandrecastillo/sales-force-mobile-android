package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades

import android.graphics.Bitmap

interface ClickEnNovedadesListener {
    fun abrirVideo(model: DetalleNovedadesModel)
    fun compartirVideo(link: String, titulo: String)
    fun compartirImagen(bitmap: Bitmap)
}
