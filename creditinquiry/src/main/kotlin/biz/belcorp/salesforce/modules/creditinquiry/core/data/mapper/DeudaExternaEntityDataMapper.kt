package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ConsultarDCResultEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.DeudaExternaEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.MotivoEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.PreCalificacionEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultarDCResult
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Motivo
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.PreCalificacion
import biz.belcorp.salesforce.modules.creditinquiry.features.model.DeudaExterna
import java.util.*

class DeudaExternaEntityDataMapper {

    fun parseConsultarDCResultObj(entity: ConsultarDCResultEntity?): ConsultarDCResult {
        val obj = ConsultarDCResult()

        entity?.apply {
            obj.estado = estado
            obj.nomEstado = nomEstado
            obj.nombre = nombre
            obj.numDocumento = numDocumento
            obj.resultado = resultado
        }

        return obj
    }

    fun parsePreCalificacion(entity: PreCalificacionEntity?): PreCalificacion {
        val obj = PreCalificacion()
        entity?.apply {
            obj.idSujeto = idSujeto
            obj.nombres = nombres
            obj.dui = dui
            obj.nit = nit
            obj.iva = iva
            obj.precalificalificacion = precalificalificacion
            obj.codigoAprobacion = codigoAprobacion
            obj.credPeorEstadoDescrip = credPeorEstadoDescrip
            obj.razonRechazo = razonRechazo
            obj.acreedores = acreedores
            obj.mensaje = mensaje
        }

        return obj
    }

    private fun parseMotivo(entity: MotivoEntity) = Motivo(entity.descripcion)

    private fun parseDeudaExterna(entity: DeudaExternaEntity?): DeudaExterna? {
        var deudaExterna: DeudaExterna? = null

        if (entity != null) {
            deudaExterna = DeudaExterna()
            deudaExterna.entidadCrediticia = entity.entidadCrediticia
            deudaExterna.monto = entity.monto
            deudaExterna.simboloMoneda = entity.simboloMoneda
        }

        return deudaExterna
    }

    fun parseMotivosList(collection: Collection<MotivoEntity>): List<Motivo> {
        val list = ArrayList<Motivo>()
        var motivo: Motivo?

        for (entity in collection) {
            motivo = parseMotivo(entity)

            list.add(motivo)
        }
        return list
    }

    fun parseDeudaExternaList(collection: Collection<DeudaExternaEntity>): List<DeudaExterna> {
        val list = ArrayList<DeudaExterna>()
        var consultaCrediticia: DeudaExterna?

        for (entity in collection) {
            consultaCrediticia = parseDeudaExterna(entity)

            if (consultaCrediticia != null) {
                list.add(consultaCrediticia)
            }
        }

        return list
    }
}
