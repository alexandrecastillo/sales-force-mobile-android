package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.helper

import androidx.annotation.IntDef

@IntDef(CategoriasIconIdentifier.PRODUCTOS,
        CategoriasIconIdentifier.FRAGANCIAS,
        CategoriasIconIdentifier.MAQUILLAJE,
        CategoriasIconIdentifier.TRATAMIENTO_FACIAL,
        CategoriasIconIdentifier.TRATAMIENTO_CORPORAL,
        CategoriasIconIdentifier.CUIDADO_DE_PIEL,
        CategoriasIconIdentifier.BIJOUTERIE)
@Retention(AnnotationRetention.RUNTIME)
annotation class CategoriasIconIdentifier {
    companion object {
        const val PRODUCTOS = 0
        const val FRAGANCIAS = 1
        const val MAQUILLAJE = 2
        const val TRATAMIENTO_FACIAL = 3
        const val TRATAMIENTO_CORPORAL = 4
        const val CUIDADO_DE_PIEL = 5
        const val BIJOUTERIE = 6
    }
}
