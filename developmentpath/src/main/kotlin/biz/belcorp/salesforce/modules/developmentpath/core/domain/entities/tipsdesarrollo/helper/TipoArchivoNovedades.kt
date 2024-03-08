package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper

@Retention(AnnotationRetention.RUNTIME)
annotation class TipoArchivoNovedades {
    companion object {
        const val IMAGEN = "IMAGEN"
        const val ARCHIVO = "ARCHIVO"
        const val VIDEO = "VIDEO"
    }
}