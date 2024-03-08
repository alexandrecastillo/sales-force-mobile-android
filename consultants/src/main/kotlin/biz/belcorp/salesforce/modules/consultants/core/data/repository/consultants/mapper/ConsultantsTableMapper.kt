package biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.utils.SingleMapper
import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.cloud.dto.ConsultantDto
import biz.belcorp.salesforce.modules.consultants.core.domain.constants.Constants

class ConsultantsTableMapper : SingleMapper<ConsultantDto.Consultant, ConsultoraEntity>() {

    override fun map(value: ConsultantDto.Consultant) = ConsultoraEntity().apply {
        consultorasId = value.id.zeroIfNull()
        region = value.region
        zona = value.zone
        seccion = value.section
        codigo = value.code.orEmpty()
        digitoVerificador = value.checkDigit
        nombre = value.personalInfo.fullName
        primerNombre = value.personalInfo.firstName
        primerApellido = value.personalInfo.surname
        segundoNombre = value.personalInfo.secondName
        segundoApellido = value.personalInfo.secondSurname
        documentoIdentidad = value.personalInfo.documentNumber
        direccion = value.personalInfo.address
        telefonoCelular = value.personalInfo.phone
        email = value.personalInfo.email
        cumpleanios = value.personalInfo.birthday
        campaniaIngreso = value.campaignAdmission
        constancia = value.constancy.new
        segmento = value.segment.description
        segmentoInternoFFVV = value.segment.description
        ultimaFacturacion = value.billingInfo.lastCampaign
        campanaPrimerPedido = value.billingInfo.firstCampaign
        saldoPendiente = value.collectionInfo.pendingDebt.toString()
        pasoPedido = if (value.salesInfo.isOrderSent) Constant.NUMBER_ONE else Constant.NUMBER_ZERO
        val hasDebt = value.collectionInfo.pendingDebt > Constant.NUMBER_ZERO
        flagDeuda = if (hasDebt) Constant.NUMBER_ONE else Constant.NUMBER_ZERO
        nivel = value.brightSegment.description
        autorizada = if (value.isAuthorized) Constants.YES_SIN_TILDE else Constants.NO
        pedido = if (value.salesInfo.isOrderSent) Constant.NUMBER_ONE else Constant.NUMBER_ZERO
        estado = value.state.code.toIntOrNull() ?: Constant.NUMBER_ZERO
        latitud = value.geoInfo.latitude.toString()
        longitud = value.geoInfo.longitude.toString()
        comparteCatalogo = value.digital.shareCatalog
        mensajeSugerido = value.digital.suggestedMessage
        digitalSegment = value.digital.digitalSegment
        hasCashPayment = value.hasCashPayment
        nivelPDV = value.brightPath.levelDescription
        constanciaPDV = value.brightPath.constancy
    }

}
