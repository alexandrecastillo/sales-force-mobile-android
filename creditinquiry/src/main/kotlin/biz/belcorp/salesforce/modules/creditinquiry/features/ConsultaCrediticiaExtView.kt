package biz.belcorp.salesforce.modules.creditinquiry.features

import biz.belcorp.salesforce.modules.creditinquiry.features.model.ExplicacionModel
import biz.belcorp.salesforce.modules.creditinquiry.features.model.MotivoModel


interface ConsultaCrediticiaExtView {
    // CREDITICIA PARA PERU
    fun mostrarAptPE()

    fun mostrarConsultoraNoApt()
    fun mostrarNombreCompleto(nombreCompleto: String)
    fun mostrarExplicaciones(explicaciones: List<ExplicacionModel>)
    fun mostrarMotivos(motivos: List<MotivoModel>)

    // CREDITICIA PARA COLOMBIA
    fun mostrarAptCO(intStatus: Int, nombre: String, estado: String)

    fun mostrarNoAptCO(intStatus: Int, nombre: String, estado: String, nomEstado: String)
    fun mostrarEstadoCO(estado: String)

    // CREDITICIA PARA ECUADOR
    fun mostrarBuroEC(dni: String, score: String, fullname: String)

    fun mostrarBuroRejectionEC(dni: String, score: String, fullname: String)

    fun mostrarResultadoBuroEC(orEmpty: String)

    // CREDITICIA PARA SALVADOR
    fun mostrarBuroSV(fullname: String, intStatus: Int)

    fun mostrarBuroResultadoNegativoSV(fullname: String, intStatus: Int)
    fun mostrarBuroSinResultadosSV(fullname: String, intStatus: Int)

    // CREIDITICIA PARA CHILE
    fun mostrarBuroResultadoCL(resultado: String)

}
