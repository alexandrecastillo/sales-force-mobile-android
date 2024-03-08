package biz.belcorp.salesforce.core.entities

import biz.belcorp.salesforce.core.db.objectbox.converters.NullToEmptyStringConverter
import biz.belcorp.salesforce.core.entities.digital.DigitalConsultantEntity
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
@Entity
data class ConsultantEntity(
    @Id var id: Long = 0,
    var campaign: String,
    var region: String,
    var zone: String,
    var section: String,
    var consultantId: Int,
    var code: String,
    var checkDigit: String,
    var multiMarca: Boolean = false,
    var multicategory: Boolean = false,
    var name: String,
    var firstName: String,
    var surname: String,
    var secondName: String,
    var secondSurname: String,
    var document: String,
    var address: String,
    @Convert(dbType = String::class, converter = NullToEmptyStringConverter::class)
    var addressReference: String,
    var phone: String,
    var email: String,
    var birthday: String,
    var anniversaryDate: String,
    var campaignAdmission: String,
    var constancyNew: String,
    var constancyEstablished: String,
    var constancyU6C: Boolean,
    var constancyU18C: Boolean,
    var shortSegmentCode: String,
    var segmentDescription: String,
    var brightSegmentCode: String,
    var brightSegmentDescription: String,
    var stateCode: String,
    var stateDescription: String,
    var isPeg: Boolean,
    var isNew: Boolean,
    var isAuthorized: Boolean,
    var isPotentialEntry: Boolean,
    var isPotentialReentry: Boolean,
    @Convert(dbType = String::class, converter = NullToEmptyStringConverter::class)
    var billingFirstCampaign: String,
    var billingLastCampaign: String,
    var billingOrderStatus: Int,
    var catalogSales: Double,
    var orderRange: String,
    var isOrderSent: Boolean,
    var pendingDebt: Double,
    var collectionPercentage: Double,
    var reentriesU18C: Int,
    var sbAmount: Double,
    var orderAmount: Double,
    var orderMtoAmount: Double,
    var isNewInconstant: Boolean,
    var shareCatalog: Boolean,
    var suggestedMessage: String?,
    var digitalSegment: String?,
    var hasCashPayment: Boolean,
    var lastModified: String
) {
    @Backlink(to = "consultantParent")
    var digitalInfo: ToMany<DigitalConsultantEntity> = ToMany(this, ConsultantEntity_.digitalInfo)
}
