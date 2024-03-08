package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.personas.SociaEmpresariaSeccionJoinned
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.*

class SociaEmpresariaMapper(private val visitaRddDBDataStore: VisitaRddDBDataStore) {

    fun parseList(models: List<SociaEmpresariaSeccionJoinned>) = models.map { parse(it) }

    fun parse(model: SociaEmpresariaSeccionJoinned): SociaEmpresariaRdd {
        validarColumnas(model)

        val seccion = model.extraerSeccion()

        val zona = ZonaRdd(
            codigo = model.zona!!,
            campania = Campania.construirDummy(),
            gerenteZona = null,
            secciones = listOf(seccion),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1
        )

        val ubicacion =
            Ubicacion(
                model.direccion,
                model.latitud?.toDouble(),
                model.longitud?.toDouble()
            )

        val directorio =
            DirectorioTelefonico(
                DirectorioTelefonico.Telefono.construirFavorito(
                    model.telefonoCelular
                ),
                DirectorioTelefonico.Telefono.construir(
                    model.telefonoCasa
                ),
                DirectorioTelefonico.Telefono.construir(
                    model.telefonoTrabajo
                )
            )

        val agendaVisitas =
            visitaRddDBDataStore.obtenerVisitasDeSociaEmpresaria(model.consultorasId)

        val sociaEmpresaria = SociaEmpresariaRdd(
            codigo = model.codigo!!,
            id = model.consultorasId,
            primerNombre = model.primerNombre,
            segundoNombre = model.segundoNombre,
            primerApellido = model.primerApellido,
            segundoApellido = model.segundoApellido,
            campaniaIngreso = model.campaniaIngreso,
            origenPedido = model.origenPedido,
            ultimaFacturacion = model.ultimaFacturacion,
            saldoPendiente = model.saldoPendiente,
            montoPedido = model.ventaGanancia,
            ventaRetail = model.ventaRetail,
            recaudoNoComisionable = model.recaudoNoComisionable,
            recaudoTotal = model.recaudoTotal,
            ganancia = model.ganancia,
            recaudoComisionable = model.recaudoComisionable,
            ventaFacturada = model.ventaFacturada,
            ventaGanancia = model.ventaGanancia,
            consultoraConsecutiva = model.consultoraConsecutiva,
            gananciaVentaRetail = model.gananciaRetail,
            email = if (model.emailFlag == 0) model.email else model.emailEdit,
            ubicacion = ubicacion,
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = model.documentoIdentidad!!,
            cumpleanios = model.cumpleanios,
            directorio = directorio,
            clasificacionLider = model.clasificacionLider,
            subClasificacionLider = model.subClasificiacionLider,
            exitosa = model.exitosa == 1,
            productividad = model.estado,
            focos = mutableListOf(),
            fechaAniversario = model.fechaAniversario.toDate(),
            fechaNacimiento = model.fechaNacimiento.toDate()
        )

        sociaEmpresaria.establecerAgenda(agendaVisitas)
        agendaVisitas.establecerPersona(sociaEmpresaria)

        sociaEmpresaria.seccion = seccion
        seccion.sociaEmpresaria = sociaEmpresaria
        zona.region = crearRegion(model)
        seccion.zona = zona

        return sociaEmpresaria
    }

    private fun crearRegion(entity: SociaEmpresariaSeccionJoinned): RegionRdd {
        return RegionRdd(
            codigo = entity.region ?: "",
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )
    }

    private fun SociaEmpresariaSeccionJoinned.extraerSeccion(): SeccionRdd {
        return SeccionRdd(
            codigo = seccion!!,
            campania = extraerCampania(),
            sociaEmpresaria = null,
            consultoras = kotlin.collections.emptyList(),
            nivel = nivel,
            planId = -1,
            visitasProgramadasInicialmente = 0,
            visitasRegistradas = 0
        )
    }

    private fun SociaEmpresariaSeccionJoinned.extraerCampania(): Campania {
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

    private fun validarColumnas(model: SociaEmpresariaSeccionJoinned) {
        if (model.documentoIdentidad == null)
            throw Exception("Documento de SE (${model.consultorasId}) inválido")

        if (model.codigo == null)
            throw Exception("Código de SE (${model.consultorasId}) inválido")

        if (model.seccion == null)
            throw Exception("Sección inválida")

        if (model.zona == null)
            throw Exception("Zona inválida")
    }
}
