package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.mapper

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.CLASSIFICATION_NUEVA


fun isSENueva(classification: String) = classification.equals(CLASSIFICATION_NUEVA, ignoreCase = true)
