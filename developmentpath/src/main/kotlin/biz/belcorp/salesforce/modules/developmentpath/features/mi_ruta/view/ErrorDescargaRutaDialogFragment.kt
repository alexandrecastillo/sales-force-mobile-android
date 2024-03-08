package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.CustomTypefaceSpan
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.core.utils.getTypeFace
import biz.belcorp.salesforce.modules.developmentpath.R
import kotlinx.android.synthetic.main.fragment_error_descarga_ruta_dialog.*

class ErrorDescargaRutaDialogFragment : BaseDialogFragment() {
    override fun getLayout() = R.layout.fragment_error_descarga_ruta_dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textoSubtitulo.text = construirTextoSubtitulo()
        botonAceptar.setOnClickListener { dismiss() }
    }

    private fun construirTextoSubtitulo(): SpannableStringBuilder {
        val typeface = requireContext().getTypeFace(TipoFuente.MULISH_BOLD.font)
        val normalString = getString(R.string.rdd_subtitulo_normal_error_demanda)
        val boldString = getString(R.string.rdd_subtitulo_bold_error_demanda)
        val fullString = normalString + boldString
        val str = SpannableStringBuilder(fullString)

        str.setSpan(
            CustomTypefaceSpan("bold", typeface),
                normalString.length,
                fullString.length - 1,
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
}
