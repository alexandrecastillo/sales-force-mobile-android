package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultarDCResult
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Motivo
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.PreCalificacion
import biz.belcorp.salesforce.modules.creditinquiry.features.model.*
import java.util.*

class DeudaExternaModelDataMapper {

    fun parseMotivo(entity: Motivo): MotivoModel {
        val motivo = MotivoModel()

        motivo.descripcion = entity.descriptcion

        return motivo
    }

    fun parseDeudaExterna(entity: DeudaExterna?): DeudaExternaModel? {
        var deudaExternaModel: DeudaExternaModel? = null

        if (entity != null) {
            deudaExternaModel = DeudaExternaModel()
            deudaExternaModel.entidadCrediticia = entity.entidadCrediticia
            deudaExternaModel.monto = entity.monto
            deudaExternaModel.simboloMoneda = entity.simboloMoneda
        }

        return deudaExternaModel
    }

    fun parseDeudaExternaList(collection: Collection<DeudaExterna>?): List<DeudaExternaModel> {
        val list = ArrayList<DeudaExternaModel>()

        collection?.forEach { entity ->
            val deudaExternaModel = parseDeudaExterna(entity)

            if (deudaExternaModel != null) {
                list.add(deudaExternaModel)
            }
        }

        return list
    }

    fun parseMotivosList(collection: Collection<Motivo>?): List<MotivoModel> {
        val list = ArrayList<MotivoModel>()
        var motivo: MotivoModel
        collection?.forEach { entity ->
            motivo = parseMotivo(entity)
            list.add(motivo)
        }

        return list
    }

    fun parsePreCalificacion(entity: PreCalificacion?): PreCalificacionModel? {
        val obj = PreCalificacionModel()

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

    fun parseDCResult(consultarDCResult: ConsultarDCResult?): ConsultarDCResultModel {
        val obj = ConsultarDCResultModel()

        consultarDCResult?.apply {
            obj.estado = estado
            obj.nomEstado = nomEstado
            obj.nombre = nombre
            obj.numDocumento = numDocumento
            obj.resultado = resultado
        }

        return obj
    }
}
