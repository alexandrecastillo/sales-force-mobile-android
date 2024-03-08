package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.*
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import java.util.*

class ConsultaCrediticiaEntityDataMapper {

    fun parseConsultaCrediticia(entity: ConsultaCrediticiaInternaEntity?): ConsultaCrediticiaInterna? {
        var consultaCrediticia: ConsultaCrediticiaInterna? = null

        if (entity != null) {
            consultaCrediticia = ConsultaCrediticiaInterna()
            consultaCrediticia.campanaIngreso = entity.campanaIngreso
            consultaCrediticia.codigoConsultora = entity.codigoConsultora
            consultaCrediticia.deudaBelcorp = entity.deudaBelcorp
            consultaCrediticia.deudaEntidadCrediticia = entity.deudaEntidadCrediticia
            consultaCrediticia.moneda = entity.moneda
            consultaCrediticia.region = entity.region
            consultaCrediticia.tipoDocumento = entity.tipoDocumento
            consultaCrediticia.ultimaCampanaFacturada = entity.ultimaCampanaFacturada
            consultaCrediticia.valoracionInterna = entity.valoracionInterna
            consultaCrediticia.estadoCliente = entity.estadoCliente
            consultaCrediticia.primerApellido = entity.primerApellido
            consultaCrediticia.segundoApellido = entity.segundoApellido
            consultaCrediticia.primerNombre = entity.primerNombre
            consultaCrediticia.segundoNombre = entity.segundoNombre
            consultaCrediticia.tipoMoneda = entity.tipoMoneda
            consultaCrediticia.motivoBloqueo = entity.motivoBloqueo
            consultaCrediticia.tieneBloqueo = entity.tieneBloqueo
            consultaCrediticia.SubEstadoPostulante = entity.subEstadoPostulante
            consultaCrediticia.cumpleValidacionesInternas = entity.cumpleValidacionesInternas
            consultaCrediticia.codestadoCliente = entity.codestadoCliente
            consultaCrediticia.autorizaPedido = entity.autorizaPedido
            consultaCrediticia.valoracionBelcorp = entity.valoracionBelcorp
            consultaCrediticia.enumEstadoCrediticio = entity.enumEstadoCrediticio
            consultaCrediticia.valid = entity.valid
            consultaCrediticia.TipoError = entity.tipoError
            consultaCrediticia.data = entity.data
            consultaCrediticia.bloqueos = BloqueoEntityDataMapper().parseBloqueo(entity.bloqueos)
        }

        return consultaCrediticia
    }

    fun parseConsultaCrediticia(entity: ConsultaCrediticia2Entity?): ConsultaCrediticia2? {
        var consultaCrediticia: ConsultaCrediticia2? = null

        if (entity != null) {

            consultaCrediticia = ConsultaCrediticia2()

            consultaCrediticia.codigoConsultora = entity.codigoConsultora
            consultaCrediticia.docIdentidad = entity.docIdentidad
            consultaCrediticia.estadoCliente = entity.estadoCliente
            consultaCrediticia.primerApellido = entity.primerApellido
            consultaCrediticia.segundoApellido = entity.segundoApellido
            consultaCrediticia.primerNombre = entity.primerNombre
            consultaCrediticia.segundoNombre = entity.segundoNombre
            consultaCrediticia.nombreCompleto = entity.nombreCompleto
            consultaCrediticia.campanaIngreso = entity.campanaIngreso
            consultaCrediticia.ultimaCampanaFacturada = entity.ultimaCampanaFacturada
            consultaCrediticia.tieneDeudas = entity.tieneDeudas
            consultaCrediticia.tieneValoracion = entity.tieneValoracion
            consultaCrediticia.resultado = entity.resultado
            consultaCrediticia.estado = entity.estado
            consultaCrediticia.respuestaServicio = entity.respuestaServicio
            consultaCrediticia.nomEstado = entity.nomEstado
            consultaCrediticia.score = entity.score
            consultaCrediticia.tipoDocumento = entity.tipoDocumento
            consultaCrediticia.deudaBelcorp = entity.deudaBelcorp
            consultaCrediticia.deudaEntidadCrediticia = entity.deudaEntidadCrediticia
            consultaCrediticia.zona = entity.zona
            consultaCrediticia.region = entity.region
            consultaCrediticia.seccion = entity.seccion
            consultaCrediticia.tipoMoneda = entity.tipoMoneda
            consultaCrediticia.moneda = entity.moneda
            consultaCrediticia.codestadoCliente = entity.codestadoCliente
            consultaCrediticia.valoracionBelcorp = entity.valoracionBelcorp
            consultaCrediticia.mensaje = entity.mensaje
            consultaCrediticia.tipoError = entity.tipoError
            consultaCrediticia.enumEstadoCrediticio = entity.enumEstadoCrediticio
            consultaCrediticia.hasError = entity.hasError
            consultaCrediticia.buro = entity.buro
            consultaCrediticia.autorizaPedido = entity.autorizaPedido
            consultaCrediticia.valid = entity.valid
            consultaCrediticia.tieneMotivos = entity.tieneMotivos
            consultaCrediticia.consultarDCResult = DeudaExternaEntityDataMapper()
                .parseConsultarDCResultObj(entity.consultarDCResult)
            consultaCrediticia.preCalificacion = DeudaExternaEntityDataMapper()
                .parsePreCalificacion(entity.preCalificacion)
            consultaCrediticia.motivos = DeudaExternaEntityDataMapper().parseMotivosList(
                entity.motivos ?: Collections.emptyList<MotivoEntity>()
            )
            consultaCrediticia.deudas = DeudaExternaEntityDataMapper().parseDeudaExternaList(
                entity.deudas ?: Collections.emptyList<DeudaExternaEntity>()
            )
            consultaCrediticia.valoraciones = ValoracionEntityDataMapper().parseValoracion(
                entity.valoraciones
                    ?: Collections.emptyList<ValoracionEntity>()
            )
            consultaCrediticia.explicaciones = ExplicacionEntityDataMapper().parseExplicacion(
                entity.explicaciones
                    ?: Collections.emptyList()
            )
            consultaCrediticia.requiereAprobacionSAC = entity.requiereAprobacionSAC
        }

        return consultaCrediticia
    }

}
