package biz.belcorp.salesforce.modules.developmentpath.features.profile.old.cabecera

import java.util.*

sealed class CabeceraPerfilModel(
    val nombres: String?,
    val iniciales: String?,
    val cumpleanios: String?,
    val telefono: String?,
    val telefonoConPrefijo: String?,
    val email: String?,
    val fechaNacimiento: Date? = null,
    val fechaAniversario: Date? = null
) {

    class PosibleConsultora(
        nombres: String?,
        iniciales: String?,
        cumpleanios: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?
    ) :
        CabeceraPerfilModel(
            nombres,
            iniciales,
            cumpleanios,
            telefono,
            telefonoConPrefijo,
            email
        )

    class GerenteZona(
        val estadoProductividad: String?,
        val esNueva: Boolean,
        val textoNueva: String?,
        nombres: String?,
        iniciales: String?,
        cumpleanios: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?
    ) :
        CabeceraPerfilModel(
            nombres,
            iniciales,
            cumpleanios,
            telefono,
            telefonoConPrefijo,
            email
        )

    class GerenteRegion(
        val estadoProductividad: String?,
        nombres: String?,
        iniciales: String?,
        cumpleanios: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?
    ) :
        CabeceraPerfilModel(nombres, iniciales, cumpleanios, telefono, telefonoConPrefijo, email)
}
