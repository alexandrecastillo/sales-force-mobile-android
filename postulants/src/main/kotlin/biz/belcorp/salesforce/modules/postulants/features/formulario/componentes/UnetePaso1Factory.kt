package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.view.View
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ViewNotImplemented
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.bo.UnetePaso1 as BO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cl.UnetePaso1 as CL
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.co.UnetePaso1 as CO
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.cr.UnetePaso1 as CR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.dm.UnetePaso1 as DM
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.ec.UnetePaso1 as EC
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.gt.UnetePaso1 as GT
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.mx.UnetePaso1 as MX
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pa.UnetePaso1 as PA
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pe.UnetePaso1 as PE
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.pr.UnetePaso1 as PR
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.sv.UnetePaso1 as SV

class UnetePaso1Factory {
    companion object {
        fun getView(mContext: Context, pais: String?, view: UnetePaso1View, mEstado: Int): View {
            return when (pais) {
                Pais.BOLIVIA.codigoIso -> BO(mContext, view)
                Pais.COLOMBIA.codigoIso -> CO(mContext, view)
                Pais.COSTARICA.codigoIso -> CR(mContext, view, mEstado)
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
