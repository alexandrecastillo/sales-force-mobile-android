package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity_Table
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import io.reactivex.Single

class DatosCobranzaConsultoraSociaDbStore {

    fun recuperar(personaId: Long): Single<ObtenerDatosCobranzaUseCase.DatosCobranza> {
        return doOnSingle {

            val where = (select
                from ConsultoraEntity::class
                where (ConsultoraEntity_Table.ConsultorasId
                eq personaId.toInt()))

            val modelo = checkNotNull(where.querySingle()) {
                Constant.RESULT_NOT_FOUND
            }

            parseDatosCobranza(modelo)
        }
    }

    private fun parseDatosCobranza(modelo: ConsultoraEntity): ObtenerDatosCobranzaUseCase.DatosCobranza {
        return ObtenerDatosCobranzaUseCase.DatosCobranza(
            consultoraConsecutiva = modelo.consultoraConsecutiva.guionSiNull(),
            ganancia = modelo.ganancia.guionSiNull(),
            gananciaVentaRetail = modelo.gananciaRetail.guionSiNull(),
            recaudoComisionable = modelo.recaudoComisionable.guionSiNull(),
            recaudoNoComisionable = modelo.recaudoNoComisionable.guionSiNull(),
            recaudoTotal = modelo.recaudoTotal.guionSiNull(),
            saldoPendiente = modelo.saldoPendiente.guionSiNull(),
            ventaFacturada = modelo.ventaFacturada.guionSiNull(),
            ventaGanancia = modelo.ventaGanancia.guionSiNull(),
            ventaRetail = modelo.ventaRetail.guionSiNull())
    }
}
