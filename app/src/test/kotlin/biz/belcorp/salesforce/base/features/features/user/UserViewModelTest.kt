@file:Suppress("EXPERIMENTAL_API_USAGE")

package biz.belcorp.salesforce.base.features.features.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.base.features.home.user.UserInfoMapper
import biz.belcorp.salesforce.base.features.home.user.UserInfoViewState
import biz.belcorp.salesforce.base.features.home.user.UserViewModel
import biz.belcorp.salesforce.base.features.utils.AppTextResolver
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.createEmoji
import biz.belcorp.salesforce.core.utils.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    private val session = mockk<Sesion>()
    private val person = mockk<Person>()
    private val campaign = mockk<Campania>()
    private val sessionUseCase = mockk<ObtenerSesionUseCase>()
    private val textResolver = mockk<AppTextResolver>()
    private val mapper = UserInfoMapper(textResolver)
    private lateinit var userViewModel: UserViewModel
    private val observer = mockk<Observer<UserInfoViewState>>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        every { session.pais } returns Pais.PERU
        every { person.firstName } returns DEFAULT_USER_NAME
        every { textResolver.formatUserDescriptionMultiProfile(*anyVararg()) } returns DEFAULT_USER_DESCRIPTION
        every { textResolver.formatUserDescriptionSE(*anyVararg()) } returns DEFAULT_USER_DESCRIPTION
        every { textResolver.formatUserLevel(*anyVararg()) } returns LEVEL
        mockkStatic(EMOJI_PACKAGE)
        every { createEmoji(any()) } returns Constant.EMPTY_STRING
        userViewModel = UserViewModel(sessionUseCase, mapper)
        userViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `test for UserInfo with rol SE level Oro`() = runBlockingTest {
        every { sessionUseCase.obtener() } returns createSession(Rol.SOCIA_EMPRESARIA)
        every { session.llaveUA } returns createKeyUAByRol(Rol.SOCIA_EMPRESARIA)
        userViewModel.getUserInformation()
        userViewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<UserInfoViewState.Success>()) }
        val response = userViewModel.viewState.value as UserInfoViewState.Success
        with(response.user) {
            name shouldNotEqual Constant.EMPTY_STRING
            description shouldNotEqual Constant.EMPTY_STRING
            level shouldNotEqual Constant.EMPTY_STRING
            colorSegment shouldEqual R.color.oro
            hasSegment shouldEqual true
        }
    }

    @Test
    fun `test for UserInfo with rol GZ`() = runBlockingTest {
        every { sessionUseCase.obtener() } returns createSession(Rol.GERENTE_ZONA)
        every { session.llaveUA } returns createKeyUAByRol(Rol.GERENTE_ZONA)
        userViewModel.getUserInformation()
        userViewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<UserInfoViewState.Success>()) }
        val response = userViewModel.viewState.value as UserInfoViewState.Success
        with(response.user) {
            name shouldNotEqual Constant.EMPTY_STRING
            description shouldNotEqual Constant.EMPTY_STRING
            hasSegment shouldEqual false
        }
    }

    @Test
    fun `test for UserInfo with rol GR`() = runBlockingTest {
        every { sessionUseCase.obtener() } returns createSession(Rol.GERENTE_REGION)
        every { session.llaveUA } returns createKeyUAByRol(Rol.GERENTE_REGION)
        userViewModel.getUserInformation()
        userViewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<UserInfoViewState.Success>()) }
        val response = userViewModel.viewState.value as UserInfoViewState.Success
        with(response.user) {
            name shouldNotEqual Constant.EMPTY_STRING
            description shouldNotEqual Constant.EMPTY_STRING
            hasSegment shouldEqual false
        }
    }

    @Test
    fun `test for UserInfo with rol DV`() = runBlockingTest {
        every { sessionUseCase.obtener() } returns createSession(Rol.DIRECTOR_VENTAS)
        every { session.llaveUA } returns createKeyUAByRol(Rol.DIRECTOR_VENTAS)
        userViewModel.getUserInformation()
        userViewModel.viewState.getOrAwaitValue()
        verify(exactly = 1) { observer.onChanged(any<UserInfoViewState.Success>()) }
        val response = userViewModel.viewState.value as UserInfoViewState.Success
        with(response.user) {
            name shouldNotEqual Constant.EMPTY_STRING
            description shouldNotEqual Constant.EMPTY_STRING
            hasSegment shouldEqual false
        }
    }

    private fun createKeyUAByRol(rol: Rol): LlaveUA {
        return when (rol) {
            Rol.SOCIA_EMPRESARIA -> LlaveUA("80", "8001", "A", 1L)
            Rol.GERENTE_ZONA -> LlaveUA("80", "8001", null, 1L)
            Rol.GERENTE_REGION -> LlaveUA("80", null, null, 1L)
            Rol.DIRECTOR_VENTAS -> LlaveUA(null, null, null, 1L)
            else -> LlaveUA(null, null, null, -1L)
        }
    }

    private fun createSession(rol: Rol): Sesion {
        return Sesion(
            null,
            null,
            ISO,
            null,
            rol.codigoRol,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            Constant.EMPTY_STRING,
            if (rol == Rol.SOCIA_EMPRESARIA) LEVEL else Constant.EMPTY_STRING,
            person,
            campaign
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    companion object {
        private const val ISO = "PE"
        private const val LEVEL = "Oro"
        private const val DEFAULT_USER_NAME = "Test user"
        private const val DEFAULT_USER_DESCRIPTION = "Description user"
        private const val EMOJI_PACKAGE = "biz.belcorp.salesforce.core.utils.EmojiUtilsKt"
    }

}
