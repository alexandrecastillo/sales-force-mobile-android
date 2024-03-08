package biz.belcorp.salesforce.modules.developmentpath.features.profile

import androidx.fragment.app.DialogFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.profile.old.PerfilDialogFragment

class PerfilBuilder private constructor(private val personIdentifier: PersonIdentifier) {

    private val fragment: DialogFragment

    init {
        this.fragment = crearInstancia()
    }

    private fun crearInstancia(): DialogFragment {
        return when (personIdentifier.role) {
            Rol.CONSULTORA,
            Rol.SOCIA_EMPRESARIA -> PerfilFragment.newInstance(personIdentifier)
            Rol.POSIBLE_CONSULTORA,
            Rol.GERENTE_ZONA,
            Rol.GERENTE_REGION -> PerfilDialogFragment.newInstance(personIdentifier)
            else -> throw UnsupportedRoleException(personIdentifier.role)
        }
    }

    fun redirigirAAcompaniamiento(): PerfilBuilder {
        if (fragment is PerfilConfigurable) {
            fragment.redirigirAAcompaniamiento()
        }
        return this
    }

    fun recuperarFragment(): DialogFragment {
        return fragment
    }

    companion object {
        fun conParametros(personIdentifier: PersonIdentifier) = PerfilBuilder(personIdentifier)
    }
}
