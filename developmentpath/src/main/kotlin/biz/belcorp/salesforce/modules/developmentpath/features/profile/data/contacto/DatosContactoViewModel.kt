package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfileInfo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import kotlinx.coroutines.launch

class DatosContactoViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : ViewModel() {

    val viewState: LiveData<DatosContactoViewState>
        get() = _viewState

    private val _viewState = MutableLiveData<DatosContactoViewState>()

    fun obtener(personIdentifier: PersonIdentifier) {
        viewModelScope.launch(handler) {
            io {
                val profileInfo = getProfileInfoUseCase.getInfo(personIdentifier)
                val datosContacto = parse(profileInfo)
                ui {
                    _viewState.value = DatosContactoViewState.ShowInfo(datosContacto)
                    mostrarOcultarVistas(personIdentifier.role)
                }
            }
        }
    }

    private fun mostrarOcultarVistas(role: Rol) {
        if (role.isPC()) {
            _viewState.value = DatosContactoViewState.PostulantsViewMode
        } else if (role.isGZ() || role.isGR()) {
            _viewState.value = DatosContactoViewState.ManagersViewMode
        }
    }

    private fun parse(datos: ProfileInfo): DatosContactoModel {
        return DatosContactoModel(
            telefonoCasa = datos.contact.homeNumberWithPrefix,
            celular = datos.contact.phoneNumberWithPrefix,
            otroTelefono = datos.contact.phoneNumberWithPrefix,
            correoElectronico = datos.contact.email,
            direccion = datos.contact.address,
            documentoIdentidad = datos.contact.document,
            cumpleanios = datos.contact.birthdate?.toDescriptionDay().orEmpty(),
            aniversario = datos.contact.anniversaryDate?.toDescriptionDay().orEmpty()
        )
    }

    private val handler = CoroutineExceptionHandler { _, _ -> }

}
