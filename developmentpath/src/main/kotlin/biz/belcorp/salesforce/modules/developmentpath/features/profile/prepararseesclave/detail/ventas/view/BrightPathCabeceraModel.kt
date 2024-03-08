package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view

import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import java.util.Date

sealed class BrightPathCabeceraModel(
    val nombres: String?,
    val iniciales: String?,
    val telefono: String?,
    val telefonoConPrefijo: String?,
    val email: String?,
    val fechaNacimiento: Date? = null,
    val fechaAniversario: Date? = null,
    val digitalSegment: String? = null
) {

    val cumpleanios: String?
        get() = fechaNacimiento?.toDescriptionDay()

    class PosibleConsultora(
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        fechaNacimiento: Date?,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        digitalSegment = digitalSegment
    )

    class Consultora(
        val segmento: String?,
        val tipo: ConsultoraRdd.Tipo,
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        val nivel: String = "",
        fechaNacimiento: Date? = null,
        fechaAniversario: Date? = null,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        fechaAniversario,
        digitalSegment = digitalSegment
    )

    class ConsultoraConNivel(
        val segmento: String?,
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        fechaNacimiento: Date?,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        digitalSegment = digitalSegment
    )

    class SociaEmpresaria(
        val exitosa: Boolean,
        val nivelProductividad: String?,
        val nivel: SociaEmpresariaRdd.Nivel? = null,
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        fechaNacimiento: Date? = null,
        fechaAniversario: Date? = null,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        fechaAniversario,
        digitalSegment = digitalSegment
    )

    class GerenteZona(
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        fechaNacimiento: Date?,
        val estadoProductividad: String,
        val esNueva: Boolean,
        val textoNueva: String,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        digitalSegment = digitalSegment
    )

    class GerenteRegion(
        nombres: String?,
        iniciales: String?,
        telefono: String?,
        telefonoConPrefijo: String?,
        email: String?,
        fechaNacimiento: Date?,
        val estadoProductividad: String,
        digitalSegment: String?
    ) : BrightPathCabeceraModel(
        nombres,
        iniciales,
        telefono,
        telefonoConPrefijo,
        email,
        fechaNacimiento,
        digitalSegment = digitalSegment
    )
}
