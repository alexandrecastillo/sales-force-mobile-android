package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import com.raizlabs.android.dbflow.sql.language.Where
import com.raizlabs.android.dbflow.structure.BaseModel

abstract class IndicatorDBDataStore<T: BaseModel>{

    fun getIndicatorData(uaKey: LlaveUA): T {
        return requireNotNull(query(uaKey).querySingle())
    }

    protected abstract fun query(uaKey: LlaveUA): Where<T>

}
