package biz.belcorp.salesforce.modules.postulants.features.formulario.postulantes

import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PrePostulanteEntity
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.modules.postulants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.postulants.core.data.entities.unete.ValidacionSMSEntity
import biz.belcorp.salesforce.modules.postulants.core.data.network.UneteApi
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulantesRequest
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PrePostulantesRequest
import org.junit.Ignore
import org.junit.Test

@Ignore("Convertir a Test Unit")
class UnetePasoServiceTest : BaseDependenciesTest() {

    val uneteApi: UneteApi by inject()
    //val accessTokenScenario: AccessTokenScenario by inject()
    val sessionUseCase: ObtenerSesionUseCase by inject()


//    @Test
//    fun `actualizar postulantes`() {
//
//        var entity = obtenerPostulanteEntityTest()
//        val actualizarPostulanteTest = uneteApi.actualizarPostulante(entity).test()
//        actualizarPostulanteTest.awaitTerminalEvent()
//
//        actualizarPostulanteTest.assertNoErrors().assertValue {
//            it.httpStatus == 200
//        }
//
//    }
//
//    @Test
//    fun `actualizar prepostulantes`() {
//
//        var entity = obtenerPrePostulanteEntityTest()
//        val actualizarPostulanteTest = uneteApi.actualizarPrePostulante(entity).test()
//        actualizarPostulanteTest.awaitTerminalEvent()
//
//        actualizarPostulanteTest.assertNoErrors().assertValue {
//            it.httpStatus == 200
//        }
//
//    }

    @Test
    fun `listar postulantes`() {

        var entity = obtenerPostulantesRequest()
        val actualizarPostulanteTest = uneteApi.listarPostulantes(entity).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }

    @Test
    fun `listar prepostulantes`() {

        var entity = obtenerPrePostulantesRequest()
        val actualizarPostulanteTest = uneteApi.listarPrePostulantes(entity).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }

    @Test
    fun `validar Bloqueos`() {

        val pais = sessionUseCase.obtener().countryIso
        val documento = "56646567"
        val tipoDocumento = "DNI"
        val zona = sessionUseCase.obtener().zona.toString()

        val actualizarPostulanteTest = uneteApi.validarBloqueos(
            pais, documento, tipoDocumento, zona
        ).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }

/*    @Test
    fun `agregar postulante`() {

        var entity = obtenerPostulanteEntityTest()
        val actualizarPostulanteTest = uneteApi.agregarPostulante(entity).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }*/

    @Test
    fun `validar mail`() {

        val pais = sessionUseCase.obtener().countryIso
        val correo = "car@hotmail.com"
        val numeroDocumento = "777777777"

        val actualizarPostulanteTest = uneteApi.validarMail(
            pais, correo, numeroDocumento).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }

    @Test
    fun `validar celular`() {

        val pais = sessionUseCase.obtener().countryIso
        val numeroCelular = "99999999"
        val tipoDocumento = "DNI"
        val numeroDocumento = "777777777"

        val actualizarPostulanteTest = uneteApi.validarCelular(
            pais, numeroCelular, tipoDocumento, numeroDocumento).test()
        actualizarPostulanteTest.awaitTerminalEvent()

        actualizarPostulanteTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }

//    @Test
//    fun `enviar validar SMS`() {
//        val entity = obtenerValidacionSMSEntityTest()
//        val enviarValidarSMSTest = uneteApi.enviarValidacionSMS(entity).test()
//        enviarValidarSMSTest.awaitTerminalEvent()
//
//        enviarValidarSMSTest.assertNoErrors().assertValue {
//            it.httpStatus == 200
//        }
//
//    }

//    @Test
//    fun `eliminar postulante`() {
//        val eliminarPostulanteTest = uneteApi.eliminarPostulante(2).test()
//        eliminarPostulanteTest.awaitTerminalEvent()
//        eliminarPostulanteTest.assertNoErrors().assertValue {
//            it.httpStatus == 200
//        }
//    }
//
//    @Test
//    fun `eliminar prePostulante`() {
//        val eliminarPrePostulanteTest = uneteApi.eliminarPrePostulante(2).test()
//        eliminarPrePostulanteTest.awaitTerminalEvent()
//        eliminarPrePostulanteTest.assertNoErrors().assertValue {
//            it.httpStatus == 200
//        }
//    }


    @Test
    fun `validar buro`() {

        val pais = sessionUseCase.obtener().countryIso
        val zona = sessionUseCase.obtener().zona.toString()
        val seccion = sessionUseCase.obtener().seccion.toString()
        val tipoDocumento = 2
        val numeroDocumento = "777777777"
        val subestado = 1
        val postulante = 2

        val validarBuroTest = uneteApi.validarBuros(
            pais, numeroDocumento, zona, seccion, tipoDocumento, subestado, postulante).test()
        validarBuroTest.awaitTerminalEvent()

        validarBuroTest.assertNoErrors().assertValue {
            it.httpStatus == 200
        }

    }


    fun obtenerValidacionSMSEntityTest(): ValidacionSMSEntity {
        var entity = ValidacionSMSEntity()
        entity.pais = sessionUseCase.obtener().countryIso
        return entity
    }

    fun obtenerPostulanteEntityTest(): PostulanteEntity {
        var entity = PostulanteEntity()
        entity.pais = sessionUseCase.obtener().countryIso
        return entity
    }

    fun obtenerPrePostulanteEntityTest(): PrePostulanteEntity {
        var entity = PrePostulanteEntity()
        entity.pais = sessionUseCase.obtener().countryIso
        return entity
    }

    fun obtenerPostulantesRequest(): PostulantesRequest {
        var request = PostulantesRequest()
        return request
    }

    fun obtenerPrePostulantesRequest(): PrePostulantesRequest {
        var request = PrePostulantesRequest()
        return request
    }


}
