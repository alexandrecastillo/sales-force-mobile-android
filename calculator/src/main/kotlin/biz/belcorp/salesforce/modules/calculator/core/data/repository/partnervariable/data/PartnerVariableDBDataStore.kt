package biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.data

import biz.belcorp.salesforce.core.entities.calculator.PartnerVariableEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class PartnerVariableDBDataStore {

    fun getPartnerVariable(): PartnerVariableEntity {
        return requireNotNull(
            Select().from(PartnerVariableEntity::class.java)
                .querySingle())
    }
}
