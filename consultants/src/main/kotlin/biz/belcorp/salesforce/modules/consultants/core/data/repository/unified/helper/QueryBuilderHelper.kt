package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper

import biz.belcorp.salesforce.core.entities.ConsultantEntity
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders.*
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.*
import io.objectbox.query.QueryBuilder
import org.koin.core.KoinComponent
import org.koin.core.inject

class QueryBuilderHelper : KoinComponent {

    private val newCycleQueryBuilder by inject<NewCycleQueryBuilder>()
    private val ordersQueryBuilder by inject<OrdersQueryBuilder>()
    private val retentionQueryBuilder by inject<RetentionQueryBuilder>()
    private val ordersStatusQueryBuilder by inject<OrdersStatusQueryBuilder>()
    private val pegsQueryBuilder by inject<PegsQueryBuilder>()
    private val billingQueryBuilder by inject<BillingQueryBuilder>()
    private val stateQueryBuilder by inject<StateQueryBuilder>()
    private val specialQueryBuilder by inject<SpecialQueryBuilder>()
    private val digitalQueryBuilder by inject<DigitalQueryBuilder>()
    private val typeQueryBuilder by inject<TypeQueryBuilder>()
    private val multibrandQueryBuilder by inject<MultibrandQueryBuilder>()
    private val multicategoryQueryBuilder by inject<MulticategoryQueryBuilder>()
    private val orderTypeQueryBuilder by inject<OrderTypeQueryBuilder>()

    fun create(builder: QueryBuilder<ConsultantEntity>, filterables: List<Filterable>) =
        builder.apply {
            filterables.forEach {
                when (it) {
                    is NewCycleFilter -> newCycleQueryBuilder.create(this, it)
                    is OrdersFilter -> ordersQueryBuilder.create(this, it)
                    is RetentionFilter -> retentionQueryBuilder.create(this, it)
                    is OrdersStatusFilter -> ordersStatusQueryBuilder.create(this, it)
                    is PegFilter -> pegsQueryBuilder.create(this, it)
                    is BillingFilter -> billingQueryBuilder.create(this, it)
                    is StateFilter -> stateQueryBuilder.create(this, it)
                    is SpecialFilter -> specialQueryBuilder.create(this, it)
                    is DigitalFilter -> digitalQueryBuilder.create(this, it)
                    is TypeFilter -> typeQueryBuilder.create(this, it)
                    is MultibrandFilter -> multibrandQueryBuilder.create(this, it)
                    is MulticategoryFilter -> multicategoryQueryBuilder.create(this, it)
                    is OrderTypeFilter -> orderTypeQueryBuilder.create(this, it)
                }
            }
        }

}
