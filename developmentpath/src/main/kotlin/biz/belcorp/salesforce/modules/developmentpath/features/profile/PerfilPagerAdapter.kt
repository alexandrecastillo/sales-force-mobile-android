package biz.belcorp.salesforce.modules.developmentpath.features.profile

import android.content.Context
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.IngresosExtraFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.AcompaniamientoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.container.AcompaniamientoCoFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.AcuerdosReconocimientosFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz.AcuerdosGzFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gr.DatosGrFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.DatosGzFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.pc.DatosPcFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.DreamFragment


class PerfilPagerAdapter(
    private val context: Context,
    private val personIdentifier: PersonIdentifier,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var botonRegistrarVisita: Button? = null

    private val acompaniamientoFragment by lazy {
        AcompaniamientoFragment
            .newInstance(personIdentifier)
            .apply { conBotonRegistroVisita(botonRegistrarVisita) }
    }

    private val acompaniamientoCoFragment by lazy {
        AcompaniamientoCoFragment.newInstance(personIdentifier)
            .apply { conBotonRegistroVisita(botonRegistrarVisita) }
    }

    override fun getItem(position: Int) = when (personIdentifier.role) {
        Rol.POSIBLE_CONSULTORA -> obtenerFragmentsDePosibleConsultora(position)
        Rol.CONSULTORA -> obtenerFragmentsDeConsultora(position)
        Rol.SOCIA_EMPRESARIA -> obtenerFragmentsDeSociaEmpresaria(position)
        Rol.GERENTE_ZONA -> obtenerFragmentsDeGZ(position)
        Rol.GERENTE_REGION -> obtenerFragmentsDeGR(position)
        else -> error("Rol no soportado por adapter")
    }

    private fun obtenerFragmentsDePosibleConsultora(position: Int): Fragment {
        return when (position) {
            Constant.ZERO_NUMBER -> DatosPcFragment.newInstance(personIdentifier)
            Constant.NUMBER_ONE -> acompaniamientoFragment
            else -> error("Posición inválida al instanciar fragments de posible consultora.")
        }
    }

    private fun obtenerFragmentsDeConsultora(position: Int): Fragment {
        return when (position) {
            Constant.ZERO_NUMBER -> TabGeneralConsultoraFragment.newInstance(personIdentifier)
            Constant.NUMBER_ONE -> DreamFragment.newInstance(personIdentifier)
            Constant.NUMBER_TWO -> acompaniamientoCoFragment
            Constant.NUMBER_THREE -> IngresosExtraFragment.newInstance(
                personIdentifier.id,
                personIdentifier.role
            )

            Constant.NUMBER_FOUR -> AcuerdosReconocimientosFragment.newInstance(personIdentifier)
            else -> error("Posición inválida al instanciar fragments de consultora.")
        }
    }

    private fun obtenerFragmentsDeSociaEmpresaria(position: Int): Fragment {
        return when (position) {
            Constant.ZERO_NUMBER -> TabGeneralConsultoraFragment.newInstance(personIdentifier)
            Constant.NUMBER_ONE -> DreamFragment.newInstance(personIdentifier)
            Constant.NUMBER_TWO -> acompaniamientoFragment
            Constant.NUMBER_THREE -> AcuerdosReconocimientosFragment.newInstance(personIdentifier)
            else -> error("Posición inválida al instanciar fragments de socia.")
        }
    }

    private fun obtenerFragmentsDeGZ(position: Int): Fragment {
        return when (position) {
            Constant.ZERO_NUMBER -> DatosGzFragment.newInstance(personIdentifier)
            Constant.NUMBER_ONE -> acompaniamientoFragment
            Constant.NUMBER_TWO -> AcuerdosGzFragment.newInstance(
                personIdentifier.id,
                personIdentifier.role
            )

            else -> error("Posición inválida al instanciar fragments de GZ.")
        }
    }

    private fun obtenerFragmentsDeGR(position: Int): Fragment {
        return when (position) {
            Constant.ZERO_NUMBER -> DatosGrFragment.newInstance(personIdentifier)
            Constant.NUMBER_ONE -> acompaniamientoFragment
            Constant.NUMBER_TWO -> AcuerdosGzFragment.newInstance(
                personIdentifier.id,
                personIdentifier.role
            )

            else -> error("Posición inválida al instanciar fragments de GR.")
        }
    }

    override fun getCount() =
        when (personIdentifier.role) {
            Rol.POSIBLE_CONSULTORA -> PAGES_FOR_PC
            Rol.CONSULTORA -> PAGES_FOR_CO
            Rol.SOCIA_EMPRESARIA -> PAGES_FOR_SE
            else -> PAGES_FOR_ANY
        }

    override fun getPageTitle(position: Int): CharSequence? =
        if (personIdentifier.role.isCO()) {
            when (position) {
                Constant.ZERO_NUMBER -> context.getString(R.string.profile)
                Constant.NUMBER_ONE -> context.getString(R.string.dream)
                Constant.NUMBER_TWO -> context.getString(R.string.accompaniment)
                Constant.NUMBER_THREE -> context.getString(R.string.competence)
                Constant.NUMBER_FOUR -> context.getString(R.string.agreements)
                else -> null
            }
        } else if (personIdentifier.role.isSE()) {
            when (position) {
                Constant.ZERO_NUMBER -> context.getString(R.string.profile)
                Constant.NUMBER_ONE -> context.getString(R.string.dream)
                Constant.NUMBER_TWO -> context.getString(R.string.accompaniment)
                Constant.NUMBER_THREE -> context.getString(R.string.agreements)
                else -> null
            }
        } else {
            when (position) {
                Constant.ZERO_NUMBER -> context.getString(R.string.profile)
                Constant.NUMBER_ONE -> context.getString(R.string.accompaniment)
                Constant.NUMBER_TWO -> context.getString(R.string.agreements)
                else -> null
            }
        }

    companion object {

        const val PAGES_FOR_PC = 2
        const val PAGES_FOR_ANY = 3
        const val PAGES_FOR_SE = 4
        const val PAGES_FOR_CO = 5
    }

}
