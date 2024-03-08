package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import biz.belcorp.salesforce.core.utils.FontStyler
import biz.belcorp.salesforce.core.utils.TipoFuente
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.barranavegacion.NivelModel
import biz.belcorp.salesforce.core.R as coreR

class ConstructorTextoNivel(val context: Context) {

    fun construir(modelo: NivelModel.Ua): SpannableStringBuilder {

        val descripcion = context.getString(
            R.string.rdd_cabecera_otra_persona_detalle,
                                            modelo.nombreCortoCampania,
                                            modelo.tipoUa,
                                            modelo.codigoUa,
                                            modelo.nombreResponsable)

        return FontStyler.establecerTexto(descripcion)
                .conDelimitador("**")
                .conContext(context)
                .conColorPrimarioDesdeRecurso(coreR.color.white)
                .conFuentePrimaria(TipoFuente.MULISH_BOLD)
                .conFuenteSecundaria(TipoFuente.MULISH_LIGHT)
                .procesar()
    }
}
