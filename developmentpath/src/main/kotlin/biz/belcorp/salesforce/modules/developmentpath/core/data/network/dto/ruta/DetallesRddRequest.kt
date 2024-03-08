package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta

import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import com.google.gson.annotations.SerializedName

class DetallesRddRequest(
    @SerializedName("detalleplanrutaRDD")
    val detalles: List<DetallePlanRutaRDDEntity>)
