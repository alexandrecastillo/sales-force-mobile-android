package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.helper

import androidx.annotation.IntDef

@IntDef(
    OtraMarcaLogoIdentifier.UNIQUE,
    OtraMarcaLogoIdentifier.NATURA,
    OtraMarcaLogoIdentifier.AVON,
    OtraMarcaLogoIdentifier.YANBAL,
    OtraMarcaLogoIdentifier.JAFRA,
    OtraMarcaLogoIdentifier.MARY_KAY,
    OtraMarcaLogoIdentifier.ORIFLAME,
    OtraMarcaLogoIdentifier.FULLER,
    OtraMarcaLogoIdentifier.VOGUE,
    OtraMarcaLogoIdentifier.AZZORTI,
    OtraMarcaLogoIdentifier.MAYBELINE,
    OtraMarcaLogoIdentifier.HINODE,
    OtraMarcaLogoIdentifier.FARMASI,
    OtraMarcaLogoIdentifier.DUPREE,
    OtraMarcaLogoIdentifier.LABDA,
    OtraMarcaLogoIdentifier.LECLEIRE,
    OtraMarcaLogoIdentifier.ARABELA,
    OtraMarcaLogoIdentifier.SCENTIA,
    OtraMarcaLogoIdentifier.ZERMAT,
    OtraMarcaLogoIdentifier.OMNILIFE,
    OtraMarcaLogoIdentifier.ESTILOS,
    OtraMarcaLogoIdentifier.VA_COMPANY
)
@Retention(AnnotationRetention.RUNTIME)
annotation class OtraMarcaLogoIdentifier {
    companion object {
        const val UNIQUE = 18
        const val NATURA = 14
        const val AVON = 2
        const val YANBAL = 20
        const val JAFRA = 9
        const val MARY_KAY = 12
        const val ORIFLAME = 16
        const val FULLER = 7
        const val VOGUE = 19
        const val AZZORTI = 3
        const val MAYBELINE = 13
        const val HINODE = 8
        const val FARMASI = 6
        const val DUPREE = 4
        const val LABDA = 10
        const val LECLEIRE = 11
        const val ARABELA = 1
        const val SCENTIA = 17
        const val ZERMAT = 21
        const val OMNILIFE = 15
        const val ESTILOS = 5
        const val VA_COMPANY = 24
    }
}
