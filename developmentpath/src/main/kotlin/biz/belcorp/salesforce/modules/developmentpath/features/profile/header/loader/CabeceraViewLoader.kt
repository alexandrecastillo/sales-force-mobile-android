package biz.belcorp.salesforce.modules.developmentpath.features.profile.header.loader

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.utils.enviarACorreo
import biz.belcorp.salesforce.core.utils.enviarAWhatsapp
import biz.belcorp.salesforce.core.utils.isToday
import biz.belcorp.salesforce.core.utils.llamarATelefono
import biz.belcorp.salesforce.core.utils.setFont
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.features.profile.header.PerfilCabeceraModel
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.clCabeceraContenedor
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.imgCorreo
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.imgCumpleanos
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.imgTelefono
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.imgWhatsapp
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.txtDigitalSegment
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.txtFechaCumpleanos
import kotlinx.android.synthetic.main.fragment_cabecera_perfil_v2.view.txtNombre
import biz.belcorp.salesforce.base.R as BaseR

abstract class CabeceraViewLoader(protected open val model: PerfilCabeceraModel) {

    protected var txtNombreCabecera: TextView? = null
    protected var txtDigitalSegment: TextView? = null
    protected var imgIndicadorCabecera: ImageView? = null
    protected var context: Context? = null

    fun bind(view: View): CabeceraViewLoader {
        mostrarNombre(view)
        mostrarSegmento(view)
        decirMostrarCumpleanioAniversario(view)
        mostrarOpcionTelefono(view)
        mostrarOpcionWhatsapp(view)
        mostraOpcionCorreo(view)
        loadRolData(view)
        return this
    }

    fun useContext(context: Context): CabeceraViewLoader {
        this.context = context
        return this
    }

    fun loadCabeceraName(txtNombre: TextView?): CabeceraViewLoader {
        txtNombreCabecera = txtNombre ?: return this
        return this
    }

    fun loadDigitalSegment(txtDSegment: TextView?): CabeceraViewLoader {
        txtDigitalSegment = txtDSegment ?: return this
        return this
    }

    fun loadIndicador(imgIndicador: ImageView?): CabeceraViewLoader {
        imgIndicadorCabecera = imgIndicador ?: return this
        return this
    }

    private fun mostrarOpcionWhatsapp(view: View) {
        model.telefonoConPrefijo?.let { telefono ->
            view.imgWhatsapp?.setOnClickListener { context?.enviarAWhatsapp(telefono) }
            view.imgWhatsapp?.visible(model.telefono.orEmpty().isNotEmpty())
        }
    }

    private fun mostrarNombre(view: View) {
        model.nombres?.let { nombres ->
            view.txtNombre?.text = nombres
        }
    }

    private fun mostrarSegmento(view: View) {
        model.digitalSegment?.let { segmento ->
            var segment = ""
            val data = model as PerfilCabeceraModel.Consultora
            if (data.tipo == ConsultoraRdd.Tipo.NUEVA) {
                segment = segmento
            } else if (!segmento.isNullOrBlank()) {
                segment = view.context.getString(R.string.bright_path_level, segmento)
            }
            view.txtDigitalSegment?.text = segment
        }
    }

    private fun mostrarOpcionTelefono(view: View) {
        model.telefono?.let { telefono ->
            view.imgTelefono?.setOnClickListener { context?.llamarATelefono(telefono) }
            view.imgTelefono?.visible(telefono.isNotEmpty())
        }
    }

    private fun mostraOpcionCorreo(view: View) {
        model.email?.let { correo ->
            view.imgCorreo?.setOnClickListener { context?.enviarACorreo(correo) }
            view.imgCorreo?.visible(correo.isNotEmpty())
        }
    }

    private fun mostrarFechaCumpleanio(view: View) {
        view.txtFechaCumpleanos?.text = model.cumpleanios
    }

    private fun decirMostrarCumpleanioAniversario(view: View) {
        val imagenCumpleanios = obtenerImagenCumpleanios(view)
        if (esSuCumpleanios()) {
            view.clCabeceraContenedor.background = imagenCumpleanios
            mostrarHoyEsSucumplenaios(view)
            return
        }
        if (esSuAniversario()) {
            view.clCabeceraContenedor.background = imagenCumpleanios
            mostrarHoyEsSuAniversario(view)
            return
        }
        mostrarIconoCumpleanioPorDefecto(view)
        mostrarFechaCumpleanio(view)
    }

    protected fun decidirMostrarTextoCabecera(): String? {
        return if (esSuCumpleanios() || esSuAniversario()) model.nombres.plus(" \uD83C\uDF81") else model.nombres
    }

    private fun obtenerImagenCumpleanios(view: View): Drawable? {
        return ContextCompat.getDrawable(view.context, R.drawable.ic_cumpleanios_confeti)
    }

    private fun esSuCumpleanios(): Boolean {
        return model.fechaNacimiento?.toCalendar()?.isToday() ?: false
    }

    private fun esSuAniversario(): Boolean {
        return model.fechaAniversario?.toCalendar()?.isToday() ?: false
    }

    private fun mostrarIconoCumpleanioPorDefecto(view: View) {
        view.imgCumpleanos?.visibility = View.VISIBLE
    }

    private fun mostrarHoyEsSucumplenaios(view: View) {
        val texto = view.context?.getString(R.string.texto_hoy_es_su_cumpleanios) ?: ""
        view.txtFechaCumpleanos?.setFont(BaseR.font.lato_regular)
        view.txtFechaCumpleanos?.setTextColor(obtenerColorIndicador(view))
        view.txtFechaCumpleanos?.text = texto
    }

    private fun mostrarHoyEsSuAniversario(view: View) {
        val texto = view.context?.getString(R.string.texto_hoy_es_su_aniversario) ?: ""
        view.txtFechaCumpleanos?.setFont(BaseR.font.lato_regular)
        view.txtFechaCumpleanos?.setTextColor(obtenerColorIndicador(view))
        view.txtFechaCumpleanos?.text = texto
    }

    protected abstract fun loadRolData(view: View)
    protected abstract fun obtenerColorIndicador(view: View): Int
}
