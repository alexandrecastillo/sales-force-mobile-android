package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.CustomTypefaceSpan
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.core.utils.getTypeFace
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_error_descarga_ruta_dialog.*

class ErrorDescargaInformacionDialogFragment : BaseDialogFragment() {

    override fun getLayout() = R.layout.fragment_error_descarga_ruta_dialog

    private var rol: Rol? = Rol.NINGUNO
    private var tipoMensaje: TipoMensaje? = TipoMensaje.MENSAJE_GENERICO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textoSubtitulo.text = obtenerSubtituloPorTipoMensaje()
        botonAceptar.setOnClickListener { dismiss() }
    }

    private fun obtenerSubtituloPorTipoMensaje(): CharSequence? {
        return when (tipoMensaje) {
            TipoMensaje.MENSAJE_POR_ROL -> construirTextoSubtituloPorRol()
            else -> construirTextoSubtituloGenerico()
        }
    }

    private fun construirTextoSubtituloGenerico(): CharSequence? {
        return getString(R.string.rdd_subtitulo_error_informacion_demanda)
    }

    private fun construirTextoSubtituloPorRol(): SpannableStringBuilder {
        val typeface = requireContext().getTypeFace(TipoFuente.MULISH_BOLD.font)
        val normalString = getString(R.string.rdd_subtitulo_error_perfil_demanda)
        val boldString = rol?.comoTexto().orEmpty()
        val fullString = normalString + boldString
        val str = SpannableStringBuilder(fullString)

        val limite = if (normalString.length < fullString.length) {
            fullString.length
        } else {
            normalString.length
        }

        str.setSpan(
            CustomTypefaceSpan("bold", typeface),
                normalString.length,
                limite,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return str
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanioAPantalla()
    }

    private fun ajustarTamanioAPantalla() {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window?.setLayout(width, height)
    }

    private fun recuperarArgs() {
        tipoMensaje = arguments?.run { getSerializable(PARAM_TIPO_MENSAJE) as TipoMensaje }
        recuperarRolSiNecesita()
    }

    private fun recuperarRolSiNecesita() {
        if (tipoMensaje == TipoMensaje.MENSAJE_POR_ROL) {
            rol = arguments?.run { getSerializable(PARAM_ROL) as Rol }
        }
    }

    enum class TipoMensaje {
        MENSAJE_POR_ROL, MENSAJE_GENERICO
    }

    companion object {

        private const val PARAM_ROL = "PARAM_ROL"
        private const val PARAM_TIPO_MENSAJE = "PARAM_TIPO_MENSAJE"

        fun newInstance(tipoMensaje: TipoMensaje, rol: Rol): ErrorDescargaInformacionDialogFragment {
            val fragment = ErrorDescargaInformacionDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(PARAM_ROL, rol)
            bundle.putSerializable(PARAM_TIPO_MENSAJE, tipoMensaje)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(tipoMensaje: TipoMensaje = TipoMensaje.MENSAJE_GENERICO): ErrorDescargaInformacionDialogFragment {
            val fragment = ErrorDescargaInformacionDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(PARAM_TIPO_MENSAJE, tipoMensaje)
            fragment.arguments = bundle
            return fragment
        }
    }
}
