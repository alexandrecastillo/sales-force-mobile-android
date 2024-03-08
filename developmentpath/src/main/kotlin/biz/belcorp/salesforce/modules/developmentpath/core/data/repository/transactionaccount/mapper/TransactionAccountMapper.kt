package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.mapper

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO_LONG
import biz.belcorp.salesforce.core.entities.transactionaccount.TransactionAccountEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.transactionaccount.cloud.dto.TransactionAccountDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDateFromShort
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cuenta.CuentaCorriente


class TransactionAccountMapper {

    fun map(dto: TransactionAccountDto): List<TransactionAccountEntity> =
        dto.transactionAccount.map { map(it) }

    private fun map(it: TransactionAccountDto.TransactionAccount): TransactionAccountEntity =
        with(it) {
            return TransactionAccountEntity(
                region = region,
                zone = zone,
                section = section,
                consultantId = consultantId,
                transactionDate = transactionDate,
                transactionDescription = transactionDescription,
                transactionType = transactionType,
                amount = amount
            )
        }

    fun map(entities: List<TransactionAccountEntity>): List<CuentaCorriente> {
        return entities.map { map(it) }
    }

    fun map(entity: TransactionAccountEntity) = CuentaCorriente(
        tipoCargoAbono = entity.transactionType,
        montoOperacion = entity.amount.toString(),
        descripcionOperacion = entity.transactionDescription,
        fechaOperacion = entity.transactionDate.toDateFromShort()
    )
}
