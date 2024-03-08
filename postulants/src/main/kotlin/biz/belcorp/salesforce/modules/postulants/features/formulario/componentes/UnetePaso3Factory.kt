package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.UnetePaso3View

class UnetePaso3Factory {
    companion object {
        fun getView(mContext: Context, pais: String?, view: UnetePaso3View): View {
            return when (pais) {
                Pais.ECUADOR.codigoIso, Pais.BOLIVIA.codigoIso, Pais.CHILE.codigoIso,
                Pais.DOMINICANA.codigoIso, Pais.PUERTORICO.codigoIso, Pais.PERU.codigoIso,
                Pais.MEXICO.codigoIso, Pais.COLOMBIA.codigoIso, Pais.COSTARICA.codigoIso
                -> UnetePaso3(mContext, view)
                else -> throw ViewNotImplemented()
            }
        }
    }
}
