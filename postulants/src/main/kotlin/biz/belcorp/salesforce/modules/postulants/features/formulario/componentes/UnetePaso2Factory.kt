package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2View
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo.UnetePaso2 as BO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl.UnetePaso2 as CL
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co.UnetePaso2 as CO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr.UnetePaso2 as CR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm.UnetePaso2 as DM
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec.UnetePaso2 as EC
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.gt.UnetePaso2 as GT
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx.UnetePaso2 as MX
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa.UnetePaso2 as PA
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe.UnetePaso2 as PE
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr.UnetePaso2 as PR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.UnetePaso2 as SV

class UnetePaso2Factory {
    companion object {
        fun getView(mContext: Context, pais: String?, view: UnetePaso2View): View {
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
