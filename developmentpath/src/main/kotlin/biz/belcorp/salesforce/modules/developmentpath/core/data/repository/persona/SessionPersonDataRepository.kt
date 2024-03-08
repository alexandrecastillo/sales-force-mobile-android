package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.persona

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataMapper
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataStore
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.primeraPalabra
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.Pais
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository


class SessionPersonDataRepository(
    private val sessionRepository: SessionRepository,
    private val userPreferences: UserSharedPreferences,
    private val campaniasDataStore: CampaniasDataStore,
    private val campaniasDataMapper: CampaniasDataMapper
) : SessionPersonRepository {

    override fun get(): Sesion {

        val sesion = sessionRepository.getSession() ?: error("Error to obtener session object")
        val persona = recuperarPersona(sesion.rol, sesion.llaveUA)

        return sesion.apply {
            this.persona = persona
        }
    }

    private fun recuperarPersona(rol: Rol, ua: LlaveUA): Persona = when (rol) {
        Rol.SOCIA_EMPRESARIA -> obtenerSocia(ua)
        Rol.GERENTE_ZONA -> obtenerGz(ua)
        Rol.GERENTE_REGION -> obtenerGr(ua)
        Rol.DIRECTOR_VENTAS -> obtenerDv(ua)
        else -> throw UnsupportedRoleException(rol)
    }

    private fun recuperarCampania(llaveUA: LlaveUA): Campania {
        val campania = campaniasDataStore.obtenerCampaniaSincrono(llaveUA)
        return campania?.let { campaniasDataMapper.parse(it) } ?: Campania.construirDummy()
    }

    private fun obtenerGr(ua: LlaveUA): GerenteRegionRdd {
        val gerenteRegion = GerenteRegionRdd(
            usuario = checkNotNull(userPreferences.username),
            estadoProductividad = "",
            fechaNacimiento = null,
            id = recuperarIdPorRol(Rol.GERENTE_REGION),
            primerNombre = userPreferences.nombre?.primeraPalabra(),
            segundoNombre = null,
            primerApellido = userPreferences.apellido?.primeraPalabra(),
            segundoApellido = null,
            email = userPreferences.correo,
            ubicacion = recuperarUbicacion(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = userPreferences.documento.orEmpty(),
            cumpleanios = null,
            directorio = DirectorioTelefonico.construirDummy()
        )

        val region = RegionRdd(
            codigo = ua.codigoRegion ?: "",
            campania = recuperarCampania(ua),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )

        gerenteRegion.region = region

        return gerenteRegion
    }

    private fun obtenerDv(ua: LlaveUA): DirectorVentas {
        val directorVentas =
            DirectorVentas(
                usuario = requireNotNull(userPreferences.username),
                estadoProductividad = Constant.EMPTY_STRING,
                fechaNacimiento = null,
                id = recuperarIdPorRol(Rol.GERENTE_REGION),
                primerNombre = userPreferences.nombre?.primeraPalabra(),
                segundoNombre = null,
                primerApellido = userPreferences.apellido?.primeraPalabra(),
                segundoApellido = null,
                email = userPreferences.correo,
                ubicacion = recuperarUbicacion(),
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                documento = userPreferences.documento.orEmpty(),
                cumpleanios = null,
                directorio = DirectorioTelefonico.construirDummy()
            )

        val pais =
            Pais(
                codigo = userPreferences.codPais.orEmpty(),
                campania = recuperarCampania(ua),
                directorVentas = directorVentas,
                regiones = emptyList()
            )

        directorVentas.pais = pais

        return directorVentas
    }

    @Deprecated(message = "Eliminar luego de refactorizar")
    private fun obtenerGz(ua: LlaveUA): GerenteZonaRdd {
        val gerenteZona = GerenteZonaRdd(
            id = recuperarIdPorRol(Rol.GERENTE_ZONA),
            usuario = requireNotNull(userPreferences.username),
            primerNombre = userPreferences.nombre?.primeraPalabra(),
            segundoNombre = null,
            primerApellido = userPreferences.apellido?.primeraPalabra(),
            segundoApellido = null,
            estadoProductividad = null,
            email = userPreferences.correo,
            ubicacion = recuperarUbicacion(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = userPreferences.documento.orEmpty(),
            cumpleanios = null,
            fechaNacimiento = null,
            directorio = DirectorioTelefonico.construirDummy(),
            esNueva = false,
            nroCampaniasComoNueva = 0
        )

        val zona = ZonaRdd(
            codigo = ua.codigoZona ?: "",
            campania = recuperarCampania(ua),
            secciones = emptyList(),
            gerenteZona = null,
            focos = mutableListOf(),
            planId = -1L,
            habilidades = mutableListOf()
        )

        val region = RegionRdd(
            codigo = ua.codigoRegion ?: "",
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )

        zona.region = region
        gerenteZona.zona = zona

        return gerenteZona
    }

    @Deprecated(message = "Eliminar luego de refactorizar")
    private fun obtenerSocia(ua: LlaveUA): SociaEmpresariaRdd {
        val sociaEmpresaria = SociaEmpresariaRdd(
            id = recuperarIdPorRol(Rol.SOCIA_EMPRESARIA),
            primerNombre = userPreferences.nombre?.primeraPalabra(),
            segundoNombre = null,
            primerApellido = userPreferences.apellido?.primeraPalabra(),
            segundoApellido = userPreferences.apellidoMaterno?.primeraPalabra(),
            campaniaIngreso = null,
            origenPedido = null,
            ventaRetail = null,
            recaudoNoComisionable = null,
            recaudoTotal = null,
            ganancia = null,
            recaudoComisionable = null,
            ventaFacturada = null,
            ventaGanancia = null,
            consultoraConsecutiva = null,
            gananciaVentaRetail = null,
            ultimaFacturacion = null,
            saldoPendiente = null,
            montoPedido = null,
            email = userPreferences.correo,
            ubicacion = recuperarUbicacion(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = requireNotNull(userPreferences.documento),
            cumpleanios = null,
            directorio = DirectorioTelefonico.construirDummy(),
            codigo = requireNotNull(userPreferences.codConsultora),
            clasificacionLider = null,
            exitosa = false,
            focos = mutableListOf(),
            productividad = null,
            subClasificacionLider = null
        )

        val region = RegionRdd(
            codigo = ua.codigoRegion ?: "",
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )

        val zona = ZonaRdd(
            codigo = ua.codigoZona ?: "",
            campania = Campania.construirDummy(),
            secciones = emptyList(),
            gerenteZona = null,
            focos = mutableListOf(),
            planId = -1L,
            habilidades = mutableListOf()
        )

        val seccion = SeccionRdd(
            codigo = ua.codigoSeccion ?: "",
            campania = recuperarCampania(ua),
            sociaEmpresaria = null,
            consultoras = emptyList(),
            nivel = null,
            planId = -1L,
            visitasProgramadasInicialmente = 0,
            visitasRegistradas = 0
        )

        zona.region = region
        seccion.zona = zona
        sociaEmpresaria.seccion = seccion

        return sociaEmpresaria
    }

    private fun recuperarUbicacion() =
        Ubicacion(
            direccion = null,
            latitud = userPreferences.latitud?.toDoubleOrNull(),
            longitud = userPreferences.longitud?.toDoubleOrNull()
        )

    private fun recuperarIdPorRol(rol: Rol): Long {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA -> userPreferences.consultoraId?.toLongOrNull()
                ?: Constant.NUMERO_CERO.toLong()
            else -> -1L
        }
    }

}
