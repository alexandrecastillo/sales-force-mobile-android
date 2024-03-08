package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.helper

import biz.belcorp.salesforce.modules.developmentpath.R

object OtraMarcaLogoFactory {
    fun from(iconId: Int): Pair<Int, Int> = when (iconId) {
        OtraMarcaLogoIdentifier.UNIQUE -> Pair(
            R.drawable.otra_marca_logo_unique,
            R.drawable.otra_marca_logo_unique_disabled
        )
        OtraMarcaLogoIdentifier.NATURA -> Pair(
            R.drawable.otra_marca_logo_natura,
            R.drawable.otra_marca_logo_natura_disabled
        )
        OtraMarcaLogoIdentifier.AVON -> Pair(
            R.drawable.otra_marca_logo_avon,
            R.drawable.otra_marca_logo_avon_disabled
        )
        OtraMarcaLogoIdentifier.YANBAL -> Pair(
            R.drawable.otra_marca_logo_yanbal,
            R.drawable.otra_marca_logo_yanbal_disabled
        )
        OtraMarcaLogoIdentifier.JAFRA -> Pair(
            R.drawable.otra_marca_logo_jafra,
            R.drawable.otra_marca_logo_jafra_disabled
        )
        OtraMarcaLogoIdentifier.MARY_KAY -> Pair(
            R.drawable.otra_marca_logo_mary_kay,
            R.drawable.otra_marca_logo_mary_kay_disabled
        )
        OtraMarcaLogoIdentifier.ORIFLAME -> Pair(
            R.drawable.otra_marca_logo_oriflame,
            R.drawable.otra_marca_logo_oriflame_disabled
        )
        OtraMarcaLogoIdentifier.FULLER -> Pair(
            R.drawable.otra_marca_logo_fuller,
            R.drawable.otra_marca_logo_fuller_disabled
        )
        OtraMarcaLogoIdentifier.VOGUE -> Pair(
            R.drawable.otra_marca_logo_vogue,
            R.drawable.otra_marca_logo_vogue_disabled
        )
        OtraMarcaLogoIdentifier.AZZORTI -> Pair(
            R.drawable.otra_marca_logo_azzorti,
            R.drawable.otra_marca_logo_azzorti_disabled
        )
        OtraMarcaLogoIdentifier.MAYBELINE -> Pair(
            R.drawable.otra_marca_logo_maybeline,
            R.drawable.otra_marca_logo_maybeline_disabled
        )
        OtraMarcaLogoIdentifier.HINODE -> Pair(
            R.drawable.otra_marca_logo_hinode,
            R.drawable.otra_marca_logo_hinode_disabled
        )
        OtraMarcaLogoIdentifier.FARMASI -> Pair(
            R.drawable.otra_marca_logo_farmasi,
            R.drawable.otra_marca_logo_farmasi_disabled
        )
        OtraMarcaLogoIdentifier.DUPREE -> Pair(
            R.drawable.otra_marca_logo_dupree,
            R.drawable.otra_marca_logo_dupree_disabled
        )
        OtraMarcaLogoIdentifier.LABDA -> Pair(
            R.drawable.otra_marca_logo_labda,
            R.drawable.otra_marca_logo_labda_disabled
        )
        OtraMarcaLogoIdentifier.LECLEIRE -> Pair(
            R.drawable.otra_marca_logo_lecleire,
            R.drawable.otra_marca_logo_lecleire_disabled
        )
        OtraMarcaLogoIdentifier.ARABELA -> Pair(
            R.drawable.otra_marca_logo_arabela,
            R.drawable.otra_marca_logo_arabela_disabled
        )
        OtraMarcaLogoIdentifier.SCENTIA -> Pair(
            R.drawable.otra_marca_logo_scentia,
            R.drawable.otra_marca_logo_scentia_disabled
        )
        OtraMarcaLogoIdentifier.ZERMAT -> Pair(
            R.drawable.otra_marca_logo_zermat,
            R.drawable.otra_marca_logo_zermat_disabled
        )
        OtraMarcaLogoIdentifier.OMNILIFE -> Pair(
            R.drawable.otra_marca_logo_omnilife,
            R.drawable.otra_marca_logo_omnilife_disabled
        )
        OtraMarcaLogoIdentifier.ESTILOS -> Pair(
            R.drawable.otra_marca_logo_estilos,
            R.drawable.otra_marca_logo_estilos_disabled
        )
        OtraMarcaLogoIdentifier.VA_COMPANY -> Pair(
            R.drawable.otra_marca_logo_vacompany,
            R.drawable.otra_marca_logo_vacompany_disabled
        )
        else -> Pair(R.drawable.ic_upload_image, R.drawable.ic_upload_image)
    }
}
