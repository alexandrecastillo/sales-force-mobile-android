package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import android.content.Context
import android.net.Uri
import androidx.annotation.StringRes
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteDocumento
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel
import biz.belcorp.salesforce.modules.postulants.utils.Constant


class UneteDocumentoFactory(val context: Context) {

    fun getDocumentos(codigoIso: String, model: PostulanteModel, esZonaCritica: Boolean= false): List<UneteDocumentoModel> {

        val listaDocumentos = when (codigoIso) {
            Pais.COLOMBIA.codigoIso -> getDocumentosCO(model)
            Pais.BOLIVIA.codigoIso -> getDocumentosBO(model)
            Pais.CHILE.codigoIso -> getDocumentosCL(model)
            Pais.COSTARICA.codigoIso -> getDocumentosCR(model)
            Pais.GUATEMALA.codigoIso -> getDocumentosGT(model)
            Pais.DOMINICANA.codigoIso -> getDocumentosDM(model)
            Pais.ECUADOR.codigoIso -> getDocumentosEC(model)
            Pais.PERU.codigoIso -> getDocumentosPE(model, esZonaCritica)
            Pais.MEXICO.codigoIso -> getDocumentosMX(model)
            Pais.PANAMA.codigoIso -> getDocumentosPA(model)
            Pais.PUERTORICO.codigoIso -> getDocumentosPR(model)
            Pais.SALVADOR.codigoIso -> getDocumentosSV(model)
            else -> listOf()
        }
        listaDocumentos.forEach {
            it.imagenRuta = getRuta(it)
        }
        return listaDocumentos
    }

    private fun getDocumentosCO(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad_anverso, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad_reverso, UneteDocumento.CDD)

        return lstDocumentos
    }

    private fun getDocumentosBO(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_documento_identidad_anverso, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_documento_identidad_reverso, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_recibo_pago_servicios, UneteDocumento.RECIBOPAGOAVAL)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_contrato, UneteDocumento.CONTRATO, false)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_documento_garante_anverso, UneteDocumento.AVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso5_documento_garante_reverso, UneteDocumento.RECIBOOTRAMARCA, false)
        lstDocumentos += model.getDocumentoModel(R.string.unete_bo_paso4_documento_solicitud_credito, UneteDocumento.CREDITOAVAL, false)

        return lstDocumentos
    }

    private fun getDocumentosCL(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_solicitud_credito, UneteDocumento.IFE, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_copia_rut, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_servicios, UneteDocumento.PAGARE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato_frontal, UneteDocumento.CONTRATO, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato_reves, UneteDocumento.CREDITOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_firma, UneteDocumento.RECIBOOTRAMARCA, false)


        return lstDocumentos
    }

    private fun getDocumentosCR(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_documento_identida, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_reverso_documento_identida, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_servicios, UneteDocumento.CONTRATO, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_autorizacion_consulta_credito_firmada, UneteDocumento.PAGARE, false)

        when (model.estadoBurocrediticio) {
            UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value.toString() -> {
                lstDocumentos += model.getDocumentoModel(R.string.documento_documento_fiador, UneteDocumento.AVAL)
                lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_de_fiador, UneteDocumento.RECIBOOTRAMARCA)
                lstDocumentos += model.getDocumentoModel(R.string.documento_autorizacion_consulta_credito_firmada_fiador, UneteDocumento.RECIBOPAGOAVAL, false)
                lstDocumentos += model.getDocumentoModel(R.string.documento_constancia_laboral_fiador, UneteDocumento.CREDITOAVAL)
                lstDocumentos += model.getDocumentoModel(R.string.documento_adjuntar_carta_descargo, UneteDocumento.CONSTANCIALABORALAVAL)
            }
            UneteEstadoCrediticio.CONDICIONADA_FIADOR.value.toString() -> {
                lstDocumentos += model.getDocumentoModel(R.string.documento_documento_fiador, UneteDocumento.AVAL)
                lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_de_fiador, UneteDocumento.RECIBOOTRAMARCA)
                lstDocumentos += model.getDocumentoModel(R.string.documento_autorizacion_consulta_credito_firmada_fiador, UneteDocumento.RECIBOPAGOAVAL, false)
                lstDocumentos += model.getDocumentoModel(R.string.documento_constancia_laboral_fiador, UneteDocumento.CREDITOAVAL)
            }
            UneteEstadoCrediticio.CONDICIONADA_CARTA_DESCARGO.value.toString() -> {
                lstDocumentos += model.getDocumentoModel(R.string.documento_adjuntar_carta_descargo, UneteDocumento.CONSTANCIALABORALAVAL)
            }
        }

        return lstDocumentos
    }

    private fun getDocumentosGT(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.dpi_frente, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.dpi_reverso, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.acuerdo_compra_y_pagare, UneteDocumento.RECIBOPAGOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.autorizacion_buro, UneteDocumento.PAGARE, false)
        lstDocumentos += model.getDocumentoModel(R.string.recibo_pago_servicios, UneteDocumento.CONTRATO)
        lstDocumentos += model.getDocumentoModel(R.string.dpi_fiador_frente, UneteDocumento.AVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.dpi_fiador_reverso, UneteDocumento.RECIBOOTRAMARCA, false)
        lstDocumentos += model.getDocumentoModel(R.string.autorizacion, UneteDocumento.CREDITOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.pagare_fiador, UneteDocumento.CONSTANCIALABORALAVAL, false)

        return lstDocumentos
    }

    private fun getDocumentosDM(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.unete_paso5_cedula_identificacion_anversa, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.unete_paso5_cedula_identificacion_reversa, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.unete_paso5_contrato, UneteDocumento.CONTRATO, false)

        return lstDocumentos
    }

    private fun getDocumentosEC(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad_anverso, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad_reverso, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato, UneteDocumento.CONTRATO, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_pagare, UneteDocumento.PAGARE, false)
        lstDocumentos += model.getDocumentoModel(R.string.recibo_pago_servicios, UneteDocumento.AVAL, false)

        return lstDocumentos
    }

    private fun getDocumentosPE(model: PostulanteModel, esZonaCritica: Boolean): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_identidad_rev, UneteDocumento.RECIBOOTRAMARCA, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_servicios, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato, UneteDocumento.CONTRATO, esZonaCritica)
        lstDocumentos += model.getDocumentoModel(R.string.documento_pagare, UneteDocumento.PAGARE, esZonaCritica)

        if (model.requiereAval == Constant.NUMERO_UNO) {
            lstDocumentos += model.getDocumentoModel(R.string.documento_aval, UneteDocumento.AVAL)
            lstDocumentos += model.getDocumentoModel(R.string.documento_aval_reverso, UneteDocumento.RECIBOPAGOAVAL, false)
        }

        return lstDocumentos
    }

    private fun getDocumentosMX(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_ife, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_ife_reverso, UneteDocumento.PAGARE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_comprobante_domicilio, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato, UneteDocumento.CONTRATO, false)

        return lstDocumentos
    }

    private fun getDocumentosPA(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.documento_cedula, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.documento_contrato_compra_venta_o_pagare_o_contado, UneteDocumento.RECIBOPAGOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_certificacion, UneteDocumento.PAGARE, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_recibo_pago_servicios, UneteDocumento.CONTRATO)
        lstDocumentos += model.getDocumentoModel(R.string.documento_cedula_fiador_frente, UneteDocumento.AVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_certificacion_fiador, UneteDocumento.CREDITOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.documento_carta_descargo, UneteDocumento.CONSTANCIALABORALAVAL, false)

        return lstDocumentos
    }

    private fun getDocumentosPR(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.unete_paso5_id_foto, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.unete_paso5_contrato, UneteDocumento.CONTRATO, false)

        return lstDocumentos
    }

    private fun getDocumentosSV(model: PostulanteModel): List<UneteDocumentoModel> {

        val lstDocumentos = mutableListOf<UneteDocumentoModel>()

        lstDocumentos += model.getDocumentoModel(R.string.dui_frente, UneteDocumento.IFE)
        lstDocumentos += model.getDocumentoModel(R.string.dui_reverso, UneteDocumento.CDD)
        lstDocumentos += model.getDocumentoModel(R.string.contrato_compra_venta_y_pagare, UneteDocumento.RECIBOPAGOAVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.autorizacion_buro_jurada, UneteDocumento.PAGARE, false)
        lstDocumentos += model.getDocumentoModel(R.string.recibo_pago_servicios, UneteDocumento.CONTRATO)
        lstDocumentos += model.getDocumentoModel(R.string.dui_fiador_frente, UneteDocumento.AVAL, false)
        lstDocumentos += model.getDocumentoModel(R.string.dui_fiador_reverso, UneteDocumento.RECIBOOTRAMARCA, false)
        lstDocumentos += model.getDocumentoModel(R.string.autorizacion_buro_jurada_fiador, UneteDocumento.CREDITOAVAL, false)

        return lstDocumentos
    }

    private fun PostulanteModel.getDocumentoModel(@StringRes resId: Int, tipo: UneteDocumento, required: Boolean = true): UneteDocumentoModel {

        val documentoModel = UneteDocumentoModel()
        val nombreDocumento = context.getString(resId)
        val asterisco = Constant.ASTERISK
        documentoModel.required = required
        documentoModel.nombre = nombreDocumento.takeIf { !required }
                ?: "$asterisco $nombreDocumento"

        when (tipo) {
            UneteDocumento.IFE -> documentoModel.documento = imagenIFE
            UneteDocumento.CDD -> documentoModel.documento = imagenCDD
            UneteDocumento.CONTRATO -> documentoModel.documento = imagenContrato
            UneteDocumento.PAGARE -> documentoModel.documento = imagenPagare
            UneteDocumento.AVAL -> documentoModel.documento = imagenDniAval
            UneteDocumento.CONSTANCIALABORALAVAL -> documentoModel.documento = imagenConstanciaLaboralAval
            UneteDocumento.RECIBOOTRAMARCA -> documentoModel.documento = imagenReciboOtraMarca
            UneteDocumento.RECIBOPAGOAVAL -> documentoModel.documento = imagenReciboPagoAval
            UneteDocumento.CREDITOAVAL -> documentoModel.documento = imagenCreditoAval
        }

        return documentoModel
    }

    private fun getRuta(documentoModel: UneteDocumentoModel): String? {
        val documento = documentoModel.documento ?: return null
        val imageUrl = documento.takeIf { !it.contains("FileProvider") }
        return imageUrl ?: Uri.parse(documento).toString()
    }

}
