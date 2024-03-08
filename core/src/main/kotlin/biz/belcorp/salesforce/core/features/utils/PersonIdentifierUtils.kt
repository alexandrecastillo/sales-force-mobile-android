package biz.belcorp.salesforce.core.features.utils

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.utils.withArguments

const val PERSON_IDENTIFIER_KEY = "PERSON_IDENTIFIER"

fun Fragment.lazyPersonIdentifier(): Lazy<PersonIdentifier> = lazy {
    requireNotNull(arguments?.getSerializable(PERSON_IDENTIFIER_KEY) as? PersonIdentifier)
}

fun <T : Fragment> T.withPersonIdentifier(personIdentifier: PersonIdentifier) = apply {
    withArguments(
        PERSON_IDENTIFIER_KEY to personIdentifier
    )
}
