package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.personas.ConsultoraCampaniaJoinned
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultantDevelopmentPath
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ConsultoraUaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.Date

class ConsultoraRDDMapper(private val visitaMapper: VisitaMapper) {

    fun parse(entidad: ConsultoraEntity): ConsultoraRdd {
        return ConsultoraRdd(
            id = entidad.consultorasId.toLong(),
            primerNombre = entidad.primerNombre,
            segundoNombre = entidad.segundoNombre,
            primerApellido = entidad.primerApellido,
            segundoApellido = entidad.segundoApellido,
            campaniaIngreso = entidad.campaniaIngreso,
            cantidadProductoPPU = entidad.cantidadProductoPPU,
            ultimaFacturacion = entidad.ultimaFacturacion,
            saldoPendiente = entidad.saldoPendiente,
            montoPedido = entidad.ventaGanancia,
            cumpleanios = entidad.cumpleanios,
            ubicacion = Ubicacion(
                direccion = entidad.direccion,
                latitud = castearADouble(entidad.latitud),
                longitud = castearADouble(entidad.longitud)
            ),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            directorio = establecerDirectorio(entidad),
            documento = entidad.documentoIdentidad ?: "",

            codigo = entidad.codigo ?: "",
            segmento = entidad.segmento,
            segmentoInterno = entidad.segmentoInternoFFVV,
            digitoVerificador = entidad.digitoVerificador,
            ventaRetail = entidad.ventaRetail,
            recaudoNoComisionable = entidad.recaudoNoComisionable,
            recaudoTotal = entidad.recaudoTotal,
            ganancia = entidad.ganancia,
            recaudoComisionable = entidad.recaudoComisionable,
            ventaFacturada = entidad.ventaFacturada,
            ventaGanancia = entidad.ventaGanancia,
            consultoraConsecutiva = entidad.consultoraConsecutiva,
            gananciaVentaRetail = entidad.gananciaRetail,
            shareCatalog = entidad.comparteCatalogo,
            suggestedMessage = entidad.mensajeSugerido
        )
    }

    fun parse(entidad: ConsultoraCampaniaJoinned): ConsultoraRdd {

        val consultora =
            ConsultoraRdd(
                id = entidad.consultorasId.toLong(),
                primerNombre = entidad.primerNombre,
                segundoNombre = entidad.segundoNombre,
                primerApellido = entidad.primerApellido,
                segundoApellido = entidad.segundoApellido,
                campaniaIngreso = entidad.campaniaIngreso,
                cantidadProductoPPU = entidad.cantidadProductoPPU,
                ultimaFacturacion = entidad.ultimaFacturacion,
                saldoPendiente = entidad.saldoPendiente,
                montoPedido = entidad.ventaGanancia,
                email = entidad.extraerEmail(),
                cumpleanios = entidad.cumpleanios,
                ubicacion = entidad.extraerUbicacion(),
                directorio = entidad.extraerDirectorio(),
                documento = entidad.documentoIdentidad.orEmpty(),
                codigo = entidad.codigo.orEmpty(),
                segmento = entidad.segmento,
                segmentoInterno = entidad.segmentoInternoFFVV,
                digitoVerificador = entidad.digitoVerificador,
                ventaRetail = entidad.ventaRetail,
                recaudoNoComisionable = entidad.recaudoNoComisionable,
                recaudoTotal = entidad.recaudoTotal,
                ganancia = entidad.ganancia,
                recaudoComisionable = entidad.recaudoComisionable,
                ventaFacturada = entidad.ventaFacturada,
                ventaGanancia = entidad.ventaGanancia,
                consultoraConsecutiva = entidad.consultoraConsecutiva,
                gananciaVentaRetail = entidad.gananciaRetail,
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                nivel = entidad.nivel,
                fechaNacimiento = entidad.fechaNacimiento.toDate()
            )

        val consultoraUa = ConsultoraUaRdd(
            codigo = consultora.codigo,
            campania = entidad.extraerCampania(),
            consultora = consultora
        )

        val seccion = SeccionRdd(
            codigo = requireNotNull(entidad.seccion),
            campania = Campania.construirDummy(),
            sociaEmpresaria = null,
            consultoras = emptyList(),
            nivel = null,
            planId = -1,
            visitasRegistradas = 0,
            visitasProgramadasInicialmente = 0
        )

        val zona = ZonaRdd(
            codigo = requireNotNull(entidad.zona),
            campania = Campania.construirDummy(),
            gerenteZona = null,
            secciones = listOf(seccion),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1
        )

        val region = RegionRdd(
            codigo = requireNotNull(entidad.region),
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            zonas = listOf(zona),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )

        zona.region = region
        seccion.zona = zona
        consultoraUa.seccion = seccion
        consultora.consultoraUa = consultoraUa

        return consultora
    }

    private fun castearADouble(str: String?): Double? {
        return try {
            str?.toDouble()
        } catch (exception: NumberFormatException) {
            null
        }
    }

    private fun ConsultoraCampaniaJoinned.extraerDirectorio(): DirectorioTelefonico {
        val telefonoCelular = telefonoCelular?.let {
            DirectorioTelefonico.Telefono(it, false)
        }
        val telefonoCasa = telefonoCasa?.let {
            DirectorioTelefonico.Telefono(it, false)
        }

        return DirectorioTelefonico(
            celular = telefonoCelular,
            casa = telefonoCasa,
            otro = null
        )
    }

    private fun ConsultoraCampaniaJoinned.extraerUbicacion(): Ubicacion {
        return Ubicacion(
            direccion = direccion,
            latitud = castearADouble(latitud),
            longitud = castearADouble(longitud)
        )
    }

    private fun ConsultoraCampaniaJoinned.extraerEmail(): String? {
        return if (emailFlag == 0) email else emailEdit
    }

    private fun ConsultoraCampaniaJoinned.extraerCampania(): Campania {
        return Campania(
            codigo = campaniaCodigo,
            nombreCorto = campaniaNombreCorto,
            inicio = campaniaInicio.toFullDate() ?: Date(),
            fin = campaniaFin.toFullDate() ?: Date(),
            inicioFacturacion = campaniaInicioFacturacion.toFullDate() ?: Date(),
            orden = campaniaOrden,
            esPrimerDiaFacturacion = esPrimerDiaFacturacion,
            periodo = Campania.construirPeriodo(periodo)
        )
    }

    private fun establecerDirectorio(entidad: ConsultoraEntity): DirectorioTelefonico {
        val telefonoCelular = entidad.telefonoCelular?.let {
            DirectorioTelefonico.Telefono(it, false)
        }
        val telefonoCasa = entidad.telefonoCasa?.let {
            DirectorioTelefonico.Telefono(it, false)
        }
        return DirectorioTelefonico(
            celular = telefonoCelular,
            casa = telefonoCasa,
            otro = null
        )
    }

    private fun parse(consultoras: List<ConsultoraEntity>) = consultoras.map { parse(it) }

    fun parse(
        consultorasDB: List<ConsultoraEntity>,
        visitasDB: List<DetallePlanRutaRDDEntity>
    ): List<ConsultoraRdd> {

        val consultoras = parse(consultorasDB)
        val seccion = recuperarSeccion(consultorasDB, consultoras)

        consultoras.forEach { consultora ->
            val visitasPorConsultora = visitaMapper
                .parse(visitasDB.filter { it.consultoraId == consultora.id })

            val consultoraUa = ConsultoraUaRdd(
                campania = Campania.construirDummy(),
                codigo = consultora.codigo,
                consultora = consultora
            )

            consultoraUa.seccion = seccion

            visitasPorConsultora.forEach { it.persona = consultora }

            consultora.establecerAgenda(AgendaVisitas(visitasPorConsultora))

            consultora.consultoraUa = consultoraUa
        }
        return consultoras
    }

    fun mapConsultant(
        consultants: List<DetallePlanRutaRDDEntity>
    ): List<ConsultantDevelopmentPath> = consultants.map { mapConsultant(it) }

    private fun mapConsultant(detail: DetallePlanRutaRDDEntity): ConsultantDevelopmentPath =
        ConsultantDevelopmentPath(detail.fechaVisita,detail.tipsid)

    private fun recuperarSeccion(
        consultorasDB: List<ConsultoraEntity>,
        consultoras: List<ConsultoraRdd>
    ): SeccionRdd {
        val codigo = if (consultorasDB.isNotEmpty()) consultorasDB[0].seccion
            ?: Constant.EMPTY_STRING else Constant.EMPTY_STRING

        return SeccionRdd(
            codigo = codigo,
            campania = Campania.construirDummy(),
            sociaEmpresaria = null,
            consultoras = consultoras,
            nivel = null,
            planId = -1,
            visitasProgramadasInicialmente = 0,
            visitasRegistradas = 0
        )
    }

}
