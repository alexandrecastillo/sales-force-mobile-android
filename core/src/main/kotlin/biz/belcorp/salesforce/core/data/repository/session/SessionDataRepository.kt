package biz.belcorp.salesforce.core.data.repository.session

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.data.preferences.AuthSharedPreferences
import biz.belcorp.salesforce.core.data.preferences.UserSharedPreferences
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataMapper
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataStore
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.SaleForceStatusCloudStore
import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.dto.SalesForceStatusDto
import biz.belcorp.salesforce.core.data.repository.session.cloud.ProfileCloudStore
import biz.belcorp.salesforce.core.data.repository.session.cloud.dto.ProfileQuery
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.people.BusinessPartner
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.people.RegionManager
import biz.belcorp.salesforce.core.domain.entities.people.SalesDirector
import biz.belcorp.salesforce.core.domain.entities.people.ZoneManager
import biz.belcorp.salesforce.core.domain.entities.saleforcestatus.SaleForceStatus
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.exceptions.NoSaleForceStatusDataException
import biz.belcorp.salesforce.core.domain.exceptions.NoUseDataException
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.deleteHyphen
import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.primeraPalabra
import biz.belcorp.salesforce.core.utils.toLevelType


class SessionDataRepository(
    private val profileCloudStore: ProfileCloudStore,
    private val userPreferences: UserSharedPreferences,
    private val authPreferences: AuthSharedPreferences,
    private val campaignDataStore: CampaniasDataStore,
    private val campaignsDataMapper: CampaniasDataMapper,
    private val saleForceStatusCloudStore: SaleForceStatusCloudStore
) : SessionRepository {

    companion object {

        @Volatile
        private var session: Sesion? = null

        @Volatile
        private var saleForceStatus: SaleForceStatus? = null

    }

    override fun getSession(): Sesion? {
        if (!esSesionActiva()) return null
        return session ?: createSession()
    }

    override fun getSaleForceStatus(): SaleForceStatus? {
        if (!esSesionActiva()) return null
        return saleForceStatus ?: createSaleForceStatus()
    }

    private fun createSession(): Sesion {
        val role = getRol()
        val ua = getAdministrativeUnit()
        session = Sesion(
            region = ua.codigoRegion,
            zona = ua.codigoZona,
            countryIso = requireNotNull(userPreferences.codPais),
            seccion = ua.codigoSeccion,
            codigoRol = requireNotNull(userPreferences.codRol),
            username = requireNotNull(userPreferences.username),
            codigoUsuario = userPreferences.codUsuario.orEmpty(),
            codigoConsultora = userPreferences.codConsultora.orEmpty(),
            nivel = userPreferences.nivel,
            nivelActual = userPreferences.nivel.orEmpty(),
            cub = userPreferences.cub.orEmpty(),
            person = getPerson(role, ua),
            campaign = getCampaign(ua)
        )

        return requireNotNull(session)
    }

    private fun createSaleForceStatus(): SaleForceStatus {
        val ua = getAdministrativeUnit()
        return SaleForceStatus(
            campaign = getCampaign(ua).codigo,
            profile = userPreferences.profile,
            region = userPreferences.region,
            zone = userPreferences.zona,
            section = userPreferences.seccion,
            level = userPreferences.level,
            achievement = userPreferences.achievement,
            productivity = userPreferences.productivity,
            classification = userPreferences.classification,
            subclassification = userPreferences.subclassification
        )
    }


    override fun esSesionActiva(): Boolean {
        return authPreferences.logged
    }

    private fun getAdministrativeUnit(): LlaveUA {

        val section = userPreferences.seccion?.ifBlank { null }
        val zone = userPreferences.zona?.ifBlank { null }
        val region = userPreferences.region?.ifBlank { null }

        return LlaveUA(
            codigoRegion = region,
            codigoZona = zone,
            codigoSeccion = section,
            consultoraId = -1L
        )
    }

    private fun getIdByRol(rol: Rol): Long {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA -> userPreferences.consultoraId?.toLongOrNull()
                ?: Constant.NUMERO_CERO.toLong()

            else -> -1L
        }
    }

    private fun getRol(): Rol {
        val rolCode = userPreferences.codRol
        return Rol.Builder.construir(rolCode)
    }

    private fun getPerson(rol: Rol, uaKey: LlaveUA) = when (rol) {
        Rol.CONSULTORA -> getGenericPerson()
        Rol.SOCIA_EMPRESARIA -> getBusinessPartner(uaKey)
        Rol.GERENTE_ZONA -> getZoneManager(uaKey)
        Rol.GERENTE_REGION -> getRegionManager(uaKey)
        Rol.DIRECTOR_VENTAS -> getSalesDirector(uaKey)
        else -> getGenericPerson()
    }

    private fun getGenericPerson(): Person {
        return Person(
            id = getIdByRol(Rol.NINGUNO),
            document = userPreferences.documento.orEmpty(),
            firstName = userPreferences.nombre?.primeraPalabra().orEmpty(),
            secondName = Constant.EMPTY_STRING,
            firstSurname = userPreferences.apellido?.primeraPalabra().orEmpty(),
            secondSurname = Constant.EMPTY_STRING,
            birthDate = Constant.EMPTY_STRING
        )
    }

    private fun getCampaign(uaKey: LlaveUA): Campania {
        val campaign = campaignDataStore.obtenerCampaniaSincrono(uaKey)
        return campaign?.let { campaignsDataMapper.parse(it) } ?: Campania.construirDummy()
    }

    private fun getZoneManager(uaKey: LlaveUA): ZoneManager {
        return ZoneManager(
            id = getIdByRol(Rol.GERENTE_ZONA),
            document = userPreferences.documento.orEmpty(),
            firstName = userPreferences.nombre?.primeraPalabra().orEmpty(),
            secondName = Constant.EMPTY_STRING,
            firstSurname = userPreferences.apellido?.primeraPalabra().orEmpty(),
            secondSurname = Constant.EMPTY_STRING,
            birthDate = Constant.EMPTY_STRING,
            productivityState = Constant.EMPTY_STRING
        ).apply { this.uaKey = getUaKey(uaKey) }
    }

    private fun getBusinessPartner(uaKey: LlaveUA): BusinessPartner {
        return BusinessPartner(
            id = getIdByRol(Rol.SOCIA_EMPRESARIA),
            document = userPreferences.documento.orEmpty(),
            firstName = userPreferences.nombre?.primeraPalabra().orEmpty(),
            secondName = Constant.EMPTY_STRING,
            firstSurname = userPreferences.apellido?.primeraPalabra().orEmpty(),
            secondSurname = Constant.EMPTY_STRING,
            birthDate = Constant.EMPTY_STRING,
            anniversaryDate = Constant.EMPTY_STRING,
            code = userPreferences.codConsultora.orEmpty(),
            levelCode = Constant.EMPTY_STRING,
            level = userPreferences.nivel.orEmpty(),
            status = Constant.EMPTY_STRING,
            leaderClassification = Constant.EMPTY_STRING
        ).apply {
            this.uaKey = getUaKey(uaKey)
            this.levelType = level.toLevelType()
        }
    }

    private fun getRegionManager(uaKey: LlaveUA): Person {
        return RegionManager(
            id = getIdByRol(Rol.NINGUNO),
            document = userPreferences.documento.orEmpty(),
            firstName = userPreferences.nombre?.primeraPalabra().orEmpty(),
            secondName = Constant.EMPTY_STRING,
            firstSurname = userPreferences.apellido?.primeraPalabra().orEmpty(),
            secondSurname = Constant.EMPTY_STRING,
            birthDate = Constant.EMPTY_STRING,
            productivityState = Constant.EMPTY_STRING
        ).apply {
            this.uaKey = getUaKey(uaKey)
        }
    }

    private fun getSalesDirector(uaKey: LlaveUA): Person {
        return SalesDirector(
            id = getIdByRol(Rol.NINGUNO),
            document = userPreferences.documento.orEmpty(),
            firstName = userPreferences.nombre?.primeraPalabra().orEmpty(),
            secondName = Constant.EMPTY_STRING,
            firstSurname = userPreferences.apellido?.primeraPalabra().orEmpty(),
            secondSurname = Constant.EMPTY_STRING,
            birthDate = Constant.EMPTY_STRING
        ).apply {
            this.uaKey = getUaKey(uaKey)
        }
    }

    override suspend fun syncProfileData() {
        val query = ProfileQuery(
            country = requireNotNull(userPreferences.codPais),
            role = requireNotNull(userPreferences.codRol),
            username = requireNotNull(userPreferences.username)
        )
        val profileResponse = profileCloudStore.getProfile(query)
        if (profileResponse.profile.isNotEmpty()) {
            saveProfileData(profileResponse.profile.first())
            updateSession()
        } else {
            throw NoUseDataException()
        }
    }

    override suspend fun updateSaleForceData(previousCampaign: String, section: String?) {
        val saleForceStatusResponse = getSaleForceStatusForSE(
            session?.pais?.codigoIso,
            session?.region,
            session?.zona,
            section ?: session?.seccion,
            previousCampaign
        )

        if (saleForceStatusResponse.isNotNull()) {
            saveSaleForceStatusData(saleForceStatusResponse)
        } else {
            throw NoSaleForceStatusDataException()
        }
    }

    private suspend fun getSaleForceStatusForSE(
        countryCode: String?,
        region: String?,
        zone: String?,
        section: String?,
        campaigns: String?
    ): SalesForceStatusDto? {

        if (countryCode.isNotNull() &&
            region.isNotNull() &&
            zone.isNotNull() &&
            section.isNotNull() &&
            campaigns.isNotNull()
        ) {
            return saleForceStatusCloudStore.getSaleForceStatus(
                countryCode!!,
                region!!,
                zone!!,
                section!!,
                campaigns!!
            )
        } else {
            return null
        }
    }

    private fun saveProfileData(profile: ProfileQuery.Data.Profile) {

        val rol = Rol.Builder.construir(profile.codRole)

        val shouldHasRegion = rol !in listOf(Rol.DIRECTOR_VENTAS)
        val shouldHasZone = rol in listOf(Rol.GERENTE_ZONA, Rol.SOCIA_EMPRESARIA)
        val shouldHasSection = rol in listOf(Rol.SOCIA_EMPRESARIA)

        userPreferences.codUsuario = profile.codUser
        userPreferences.nombre = profile.names
        userPreferences.apellido = profile.firstSurname
        userPreferences.apellidoMaterno = profile.secondSurname
        userPreferences.pais = profile.countryName
        userPreferences.codPais = profile.codCountry
        userPreferences.region = profile.codRegion.takeIf { shouldHasRegion }
        userPreferences.zona = profile.codZone.takeIf { shouldHasZone }
        userPreferences.codRol = profile.codRole
        userPreferences.cub = profile.cub
        userPreferences.seccion = profile.codSection.takeIf { shouldHasSection }
        userPreferences.nivel = profile.level
        userPreferences.telefono = profile.landline
        userPreferences.celular = profile.cellphone
        userPreferences.correo = profile.email
        userPreferences.documento = profile.document
        userPreferences.codConsultora = profile.codConsultant
        userPreferences.consultoraId = profile.consultantId.toString()
        userPreferences.latitud = profile.latitude
        userPreferences.longitud = profile.longitude
        userPreferences.codTerritorio = profile.codTerritory

    }

    private fun saveSaleForceStatusData(saleForceStatusResponse: SalesForceStatusDto?) {
        userPreferences.profile = saleForceStatusResponse?.profile
        userPreferences.level = saleForceStatusResponse?.level
        userPreferences.achievement = saleForceStatusResponse?.achievement
        userPreferences.productivity = saleForceStatusResponse?.productivity
        userPreferences.classification = saleForceStatusResponse?.classification
        userPreferences.subclassification = saleForceStatusResponse?.subclassification
    }

    private fun getUaKey(uaKey: LlaveUA): LlaveUA {
        return LlaveUA(
            uaKey.codigoRegion.orEmpty().deleteHyphen(),
            uaKey.codigoZona.orEmpty().deleteHyphen(),
            uaKey.codigoSeccion.orEmpty().deleteHyphen(),
            uaKey.consultoraId
        )
    }

    override fun updateSession() {
        session = createSession()
    }

    override fun isAvailable(): Boolean {
        val isLoginInProgress = authPreferences.loginInProgress
        if (isLoginInProgress) authPreferences.logged = false
        return authPreferences.logged
    }

    override fun setSessionState(hasError: Boolean) {
        authPreferences.loginInProgress = false
        authPreferences.logged = !hasError
    }

}
