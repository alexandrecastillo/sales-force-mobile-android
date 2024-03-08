package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.UnetePaso5View
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo.UnetePaso5 as BO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl.UnetePaso5 as CL
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co.UnetePaso5 as CO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr.UnetePaso5 as CR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm.UnetePaso5 as DM
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec.UnetePaso5 as EC
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.gt.UnetePaso5 as GT
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx.UnetePaso5 as MX
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa.UnetePaso5 as PA
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe.UnetePaso5 as PE
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr.UnetePaso5 as PR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.UnetePaso5 as SV


class UnetePaso5Factory {
    companion object {
        fun getView(mContext: Context, pais: String?, view: UnetePaso5View): View {
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
