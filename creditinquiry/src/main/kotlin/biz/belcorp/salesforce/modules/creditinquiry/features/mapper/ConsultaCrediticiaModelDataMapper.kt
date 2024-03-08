package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticia2Model
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInternaModel
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaModel


class ConsultaCrediticiaModelDataMapper {

    fun parseConsultaCrediticia(consultaCrediticia: ConsultaCrediticiaInterna?): ConsultaCrediticiaInternaModel? {
        var m: ConsultaCrediticiaInternaModel? = null

        if (consultaCrediticia != null) {
            m = ConsultaCrediticiaInternaModel()

            m.pais = consultaCrediticia.pais
            m.nombreCompleto = consultaCrediticia.nombreCompleto
            m.motivoBloqueo = consultaCrediticia.motivoBloqueo
            m.tieneBloqueo = consultaCrediticia.tieneBloqueo
            m.EstadoPostulante = consultaCrediticia.EstadoPostulante
            m.SubEstadoPostulante = consultaCrediticia.SubEstadoPostulante
            m.cumpleValidacionesInternas = consultaCrediticia.cumpleValidacionesInternas
            m.zona = consultaCrediticia.zona
            m.seccion = consultaCrediticia.seccion
            m.codestadoCliente = consultaCrediticia.codestadoCliente
            m.autorizaPedido = consultaCrediticia.autorizaPedido
            m.valoracionBelcorp = consultaCrediticia.valoracionBelcorp
            m.enumEstadoCrediticio = consultaCrediticia.enumEstadoCrediticio
            m.mensaje = consultaCrediticia.mensaje
            m.valid = consultaCrediticia.valid
            m.TipoError = consultaCrediticia.TipoError
            m.campanaIngreso = consultaCrediticia.campanaIngreso
            m.codigoConsultora = consultaCrediticia.codigoConsultora
            m.deudaBelcorp = consultaCrediticia.deudaBelcorp
            m.deudaEntidadCrediticia = consultaCrediticia.deudaEntidadCrediticia
            m.documentoIdentidad = consultaCrediticia.documentoIdentidad
            m.moneda = consultaCrediticia.moneda
            m.region = consultaCrediticia.region
            m.tipoDocumento = consultaCrediticia.tipoDocumento
            m.ultimaCampanaFacturada = consultaCrediticia.ultimaCampanaFacturada
            m.valoracionInterna = consultaCrediticia.valoracionInterna
            m.estadoCliente = consultaCrediticia.estadoCliente
            m.primerApellido = consultaCrediticia.primerApellido
            m.segundoApellido = consultaCrediticia.segundoApellido
            m.primerNombre = consultaCrediticia.primerNombre
            m.segundoNombre = consultaCrediticia.segundoNombre
            m.tipoMoneda = consultaCrediticia.tipoMoneda
            m.bloqueos = BloqueoModelDataMapper().parseBloqueo(consultaCrediticia.bloqueos)
            m.data = consultaCrediticia.data
        }

        return m
    }

    fun parseConsultaCrediticia(consultaCrediticia: ConsultaCrediticia?): ConsultaCrediticiaModel? {
        var consultaCrediticiaModel: ConsultaCrediticiaModel? = null

        if (consultaCrediticia != null) {
            consultaCrediticiaModel = ConsultaCrediticiaModel()
            consultaCrediticiaModel.campanaIngreso = consultaCrediticia.campanaIngreso
            consultaCrediticiaModel.codigoConsultora = consultaCrediticia.codigoConsultora
            consultaCrediticiaModel.deudaBelcorp = consultaCrediticia.deudaBelcorp
            consultaCrediticiaModel.deudaEntidadCrediticia =
                consultaCrediticia.deudaEntidadCrediticia
            consultaCrediticiaModel.docIdentidad = consultaCrediticia.docIdentidad
            consultaCrediticiaModel.moneda = consultaCrediticia.moneda
            consultaCrediticiaModel.region = consultaCrediticia.region
            consultaCrediticiaModel.tipoDocumento = consultaCrediticia.tipoDocumento
            consultaCrediticiaModel.ultimaCampanaFacturada =
                consultaCrediticia.ultimaCampanaFacturada
            consultaCrediticiaModel.valoracionInterna = consultaCrediticia.valoracionInterna
            consultaCrediticiaModel.estadoCliente = consultaCrediticia.estadoCliente
            consultaCrediticiaModel.primerApellido = consultaCrediticia.primerApellido
            consultaCrediticiaModel.segundoApellido = consultaCrediticia.segundoApellido
            consultaCrediticiaModel.primerNombre = consultaCrediticia.primerNombre
            consultaCrediticiaModel.segundoNombre = consultaCrediticia.segundoNombre
            consultaCrediticiaModel.tipoMoneda = consultaCrediticia.tipoMoneda
            consultaCrediticiaModel.valoracionExterna = consultaCrediticia.valoracionExterna
            consultaCrediticiaModel.motivoBloqueoDeudaInternaList =
                MotivoBloqueoDeudaInternaModelDataMapper()
                    .parseMotivoBloqueoDeudaInternaList(consultaCrediticia.motivoBloqueoDeudaInternaList)
            consultaCrediticiaModel.deudasExternasList = DeudaExternaModelDataMapper()
                .parseDeudaExternaList(consultaCrediticia.deudasExternasList)
        }

        return consultaCrediticiaModel
    }

    fun parseConsultaCrediticia(consultaCrediticia: ConsultaCrediticia2?): ConsultaCrediticia2Model {
        val consultaCrediticiaModel = ConsultaCrediticia2Model()

        if (consultaCrediticia != null) {
            consultaCrediticiaModel.codigoConsultora = consultaCrediticia.codigoConsultora
            consultaCrediticiaModel.docIdentidad = consultaCrediticia.docIdentidad
            consultaCrediticiaModel.estadoCliente = consultaCrediticia.estadoCliente
            consultaCrediticiaModel.primerApellido = consultaCrediticia.primerApellido
            consultaCrediticiaModel.segundoApellido = consultaCrediticia.segundoApellido
            consultaCrediticiaModel.primerNombre = consultaCrediticia.primerNombre
            consultaCrediticiaModel.segundoNombre = consultaCrediticia.segundoNombre
            consultaCrediticiaModel.nombreCompleto = consultaCrediticia.nombreCompleto
            consultaCrediticiaModel.campanaIngreso = consultaCrediticia.campanaIngreso
            consultaCrediticiaModel.ultimaCampanaFacturada =
                consultaCrediticia.ultimaCampanaFacturada
            consultaCrediticiaModel.tieneDeudas = consultaCrediticia.tieneDeudas
            consultaCrediticiaModel.tieneValoracion = consultaCrediticia.tieneValoracion
            consultaCrediticiaModel.tieneDeudas = consultaCrediticia.tieneDeudas
            consultaCrediticiaModel.resultado = consultaCrediticia.resultado
            consultaCrediticiaModel.estado = consultaCrediticia.estado
            consultaCrediticiaModel.respuestaServicio = consultaCrediticia.respuestaServicio
            consultaCrediticiaModel.nomEstado = consultaCrediticia.nomEstado
            consultaCrediticiaModel.score = consultaCrediticia.score
            consultaCrediticiaModel.tipoDocumento = consultaCrediticia.tipoDocumento
            consultaCrediticiaModel.deudaBelcorp = consultaCrediticia.deudaBelcorp
            consultaCrediticiaModel.deudaEntidadCrediticia =
                consultaCrediticia.deudaEntidadCrediticia
            consultaCrediticiaModel.region = consultaCrediticia.region
            consultaCrediticiaModel.zona = consultaCrediticia.zona
            consultaCrediticiaModel.seccion = consultaCrediticia.seccion
            consultaCrediticiaModel.buro = consultaCrediticia.buro
            consultaCrediticiaModel.tipoMoneda = consultaCrediticia.tipoMoneda
            consultaCrediticiaModel.moneda = consultaCrediticia.moneda
            consultaCrediticiaModel.codestadoCliente = consultaCrediticia.codestadoCliente
            consultaCrediticiaModel.valoracionBelcorp = consultaCrediticia.valoracionBelcorp
            consultaCrediticiaModel.mensaje = consultaCrediticia.mensaje
            consultaCrediticiaModel.tipoError = consultaCrediticia.tipoError
//            consultaCrediticiaModel.data = consultaCrediticia.data
            consultaCrediticiaModel.enumEstadoCrediticio = consultaCrediticia.enumEstadoCrediticio
            consultaCrediticiaModel.isConsultantApt = consultaCrediticia.isConsultantApt
            consultaCrediticiaModel.consultarDCResult = DeudaExternaModelDataMapper()
                .parseDCResult(consultaCrediticia.consultarDCResult)


            consultaCrediticiaModel.preCalificacion = DeudaExternaModelDataMapper()
                .parsePreCalificacion(consultaCrediticia.preCalificacion)
            consultaCrediticiaModel.motivos = DeudaExternaModelDataMapper()
                .parseMotivosList(consultaCrediticia.motivos)
            consultaCrediticiaModel.deudas = DeudaExternaModelDataMapper()
                .parseDeudaExternaList(consultaCrediticia.deudas)
            consultaCrediticiaModel.valoraciones = ValoracionModelDataMapper()
                .parseValoracion(consultaCrediticia.valoraciones)
            consultaCrediticiaModel.explicaciones = ExplicacionModelDataMapper()
                .parseExplicacion(consultaCrediticia.explicaciones)
        }

        return consultaCrediticiaModel
    }
}
