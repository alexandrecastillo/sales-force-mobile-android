package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador.BuscadorRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.BusquedaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card.MiRutaCardMapper
import java.text.SimpleDateFormat
import java.util.*

class BuscadorMapper(private val miRutaCardMapper: MiRutaCardMapper) {

    fun map(resultado: BuscadorRdd.Resultado): List<BusquedaViewModel.Seccion> {

        val secciones = mutableListOf<BusquedaViewModel.Seccion>()
        val personas = separarPersonas(resultado.personasNoPlanificadas)

        if (personas.proActivas.isNotEmpty())
            secciones.add(mapPosiblesConsultoras(personas.proActivas))

        if (personas.noProActivas.isNotEmpty())
            secciones.add(mapPosiblesConsultorasNoPro(personas.noProActivas))

        if (personas.noPosiblesConsultoras.isNotEmpty())
            secciones.add(mapNoPlanificadas(personas.noPosiblesConsultoras))

        secciones.addAll(mapPlanificadas(resultado.dias))

        return secciones
    }


    private fun separarPersonas(personas: List<PersonaRdd>): BusquedaPersonas {

        val (posiblesConsultoras, noPosiblesConsultoras) =
                personas.partition { it is PosibleConsultoraRdd }

        val proActivas = posiblesConsultoras
                .map { it as PosibleConsultoraRdd }
                .filter { it.tipo == PosibleConsultoraRdd.Tipo.PROACTIVA }

        val noProactivas = posiblesConsultoras
                .map { it as PosibleConsultoraRdd }
                .filter { it.tipo == PosibleConsultoraRdd.Tipo.NO_PROACTIVA }

        return BusquedaPersonas(
                noPosiblesConsultoras = noPosiblesConsultoras,
                proActivas = proActivas,
                noProActivas = noProactivas)
    }

    private fun mapPlanificadas(dias: List<Dia>): List<BusquedaViewModel.Seccion> {
        return dias.map { mapPlanificadas(it) }
    }

    private fun mapPlanificadas(dia: Dia) =
            BusquedaViewModel.Seccion(
                    fecha = obtenerTitulo(dia.fecha),
                    tipoTituloSeccion = BusquedaViewModel.TipoTituloSeccion.FECHA,
                    personas = miRutaCardMapper.map(dia.visitasProgramadas))

    private fun mapNoPlanificadas(personas: List<PersonaRdd>) =
            BusquedaViewModel.Seccion(
                    tipoTituloSeccion = BusquedaViewModel.TipoTituloSeccion.NO_PLANIFICADAS,
                    personas = miRutaCardMapper.map(personas))


    private fun mapPosiblesConsultoras(posiblesConsultoras: List<PosibleConsultoraRdd>) =
            BusquedaViewModel.Seccion(
                    tipoTituloSeccion = BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_PROACTIVAS,
                    personas = miRutaCardMapper.map(posiblesConsultoras))

    private fun mapPosiblesConsultorasNoPro(posiblesConsultoras: List<PosibleConsultoraRdd>) =
            BusquedaViewModel.Seccion(
                    tipoTituloSeccion = BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_NO_PROACTIVAS,
                    personas = miRutaCardMapper.map(posiblesConsultoras))

    private fun obtenerTitulo(fecha: Calendar): String {
        val df = SimpleDateFormat("dd 'de' MMMM", Locale.getDefault())

        return df.format(fecha.time)
    }

    class BusquedaPersonas(val noPosiblesConsultoras: List<PersonaRdd>,
                           val proActivas: List<PosibleConsultoraRdd>,
                           val noProActivas: List<PosibleConsultoraRdd>)
}
