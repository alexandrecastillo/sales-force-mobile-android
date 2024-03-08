package biz.belcorp.salesforce.modules.calculator.core.data.repository.paymentdetail.data

import biz.belcorp.salesforce.core.entities.calculator.PaymentDetailEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import com.raizlabs.android.dbflow.sql.language.Select
import io.reactivex.Single

class PaymentDetailDBDataStore {

    fun all(): Single<List<PaymentDetailEntity>> {

        return doOnSingle {
            Select().from(PaymentDetailEntity::class.java)
                .queryList()

        }
    }
}
