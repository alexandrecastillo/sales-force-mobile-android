package biz.belcorp.salesforce.modules.calculator.core.data.repository.fixedamount.data

import biz.belcorp.salesforce.core.entities.sql.calculator.CalculadoraMontoFijoEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import io.reactivex.Single

class CalculadoraMontoFijoDBDataStore {

    fun all(): Single<List<CalculadoraMontoFijoEntity>> =
        doOnSingle { requireNotNull((select from CalculadoraMontoFijoEntity::class).queryList()) }
}
