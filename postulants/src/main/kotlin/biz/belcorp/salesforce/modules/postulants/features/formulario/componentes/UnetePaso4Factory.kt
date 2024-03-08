package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4View
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo.UnetePaso4 as BO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl.UnetePaso4 as CL
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co.UnetePaso4 as CO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr.UnetePaso4 as CR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm.UnetePaso4 as DM
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec.UnetePaso4 as EC
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.gt.UnetePaso4 as GT
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx.UnetePaso4 as MX
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa.UnetePaso4 as PA
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe.UnetePaso4 as PE
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr.UnetePaso4 as PR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.UnetePaso4 as SV

class UnetePaso4Factory {
    companion object {
        fun getView(mContext: Context, pais: String?, view: UnetePaso4View): View {
            return when (pais) {
                Pais.BOLIVIA.codigoIso -> BO(mContext, view)
                Pais.COLOMBIA.codigoIso -> CO(mContext, view)
                Pais.COSTARICA.codigoIso -> CR(mContext, view)
                Pais.CHILE.codigoIso -> CL(mContext, view)
                Pais.PERU.codigoIso -> PE(mContext, view)
                Pais.PUERTORICO.codigoIso -> PR(mContext, view)
                Pais.ECUADOR.codigoIso -> EC(mContext, view)
                Pais.PANAMA.codigoIso -> PA(mContext, view)
                Pais.MEXICO.codigoIso -> MX(mContext, view)
                Pais.SALVADOR.codigoIso -> SV(mContext, view)
                Pais.DOMINICANA.codigoIso -> DM(mContext, view)
                Pais.GUATEMALA.codigoIso -> GT(mContext, view)
                else -> throw ViewNotImplemented()
            }
        }
    }
}
