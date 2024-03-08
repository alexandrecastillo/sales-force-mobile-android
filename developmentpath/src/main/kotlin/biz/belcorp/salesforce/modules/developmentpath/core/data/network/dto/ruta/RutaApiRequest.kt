package biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta

import biz.belcorp.salesforce.modules.developmentpath.BuildConfig

class RutaApiRequest(
    var origen: String,
    var destino: String,
    var optimize: Boolean = true,
    var modo: String = "walking",
    var wayPoints: String) {

    fun buildUrl(): String {
        return BuildConfig.API_ROUTE_GOOGLE + buildQuery()
    }

    private fun buildQuery(): String {
        val orig = "origin=$origen"
        val dest = "destination=$destino"
        val optm = "optimize:$optimize|"
        val mod = "mode=$modo"
        var wayPoin = ""
        val key = "key=" + BuildConfig.GOOGLE_API_KEY
        if (wayPoints.isNotEmpty()) {
            wayPoin = "waypoints=$optm$wayPoints"
        }
        return "$orig&$dest&$wayPoin&$mod&$key"
    }
}
