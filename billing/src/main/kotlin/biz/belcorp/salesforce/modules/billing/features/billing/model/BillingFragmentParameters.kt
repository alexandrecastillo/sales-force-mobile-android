package biz.belcorp.salesforce.modules.billing.features.billing.model

import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class BillingFragmentParameters(
    role: Rol,
    var uaKey: LlaveUA,
    val isParent: Boolean
) : FragmentParameters(role)
