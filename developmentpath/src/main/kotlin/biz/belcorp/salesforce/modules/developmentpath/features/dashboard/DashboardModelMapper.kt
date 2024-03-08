package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.Foco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.AvanceSeccionesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.FocosHabilidadesEquipoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.focos.FocosZonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosYHabilidadesPorUa
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel

class AvanceModelMapper {

    fun map(response: AvanceSeccionesUseCase.Response): AvanceViewModel {
        return AvanceViewModel(
                response.secciones.map { map(it) })
    }

    fun map(seccion: SeccionRdd): SeccionAvanceModel {
        return SeccionAvanceModel(
                planId = seccion.planId, codigoSeccion = seccion.codigo,
                seccionFuePlanificada = seccion.fuePlanificada,
                nombreSocia = seccion.sociaEmpresaria?.nombreApellido ?: "",
                visitadas = seccion.visitasRegistradas.toString(),
                total = seccion.visitasProgramadasInicialmente.toString(),
                progreso = seccion.progreso.toInt(), nivel = seccion.nivel ?: "",
                exito = obtenerExito(seccion.sociaEmpresaria?.exitosa ?: false),
                estado = seccion.sociaEmpresaria?.productividad ?: "",
                coberturada = seccion.coberturada,
                llaveUA = seccion.llave,
                visitedDays = seccion.visitedDays)
    }

    private fun obtenerExito(exitosa: Boolean): SeccionAvanceModel.Exito {
        return if (exitosa) {
            SeccionAvanceModel.Exito("Exitosa", SeccionAvanceModel.Exito.Color.VERDE)
        } else {
            SeccionAvanceModel.Exito("No exitosa", SeccionAvanceModel.Exito.Color.ROJO)
        }
    }
}

class FocoModelMapper {

    fun map(response: FocosZonaUseCase.Response): FocosGzViewModel {
        return FocosGzViewModel(
            response.secciones.map { map(it) })
    }

    fun map(seccion: SeccionRdd): SeccionFocoModel {
        return SeccionFocoModel(
            codigoSeccion = seccion.codigo,
            sociaId = seccion.sociaEmpresaria?.id,
            nombresSocia = parsearNombreSocia(seccion),
            coberturada = seccion.coberturada,
            mostrarEditar = seccion.coberturada,
            mostrarFocos = seccion.sociaEmpresaria?.focos?.isNotEmpty() ?: false,
            focos = parsearFocos(seccion.sociaEmpresaria?.focos)
        )
    }

    private fun parsearNombreSocia(seccion: SeccionRdd) =
            WordUtils.capitalizeFully(seccion.sociaEmpresaria?.nombreApellido ?: "")

    private fun parsearFocos(focos: MutableList<Foco>?): List<SeccionFocoModel.FocoModel> {
        return focos?.map { parsearFoco(it) } ?: emptyList()
    }

    private fun parsearFoco(foco: Foco): SeccionFocoModel.FocoModel {
        return SeccionFocoModel.FocoModel(foco.id, foco.descripcion)
    }
}

class FocoRegionModelMapper {

    fun map(response: FocosHabilidadesEquipoUseCase.Response): FocosHabilidadesPorUaViewModel {
        return when(response) {
            is FocosHabilidadesEquipoUseCase.Response.Regiones ->
                FocosHabilidadesPorUaViewModel(response.regiones.map { map(it) })
            is FocosHabilidadesEquipoUseCase.Response.Zonas ->
                FocosHabilidadesPorUaViewModel(response.zonas.map { map(it) })
        }
    }

    fun map(region: RegionRdd): FocosYHabilidadesPorUa {
        return FocosYHabilidadesPorUa(
                codigoUa = region.codigo,
                personaId = region.gerenteRegion?.id,
                nombresPersona = WordUtils.capitalizeFully(region.gerenteRegion?.nombreApellido ?: ""),
                codigoCampania = region.campania.nombreCorto,
                ua = region,
                coberturada = region.coberturada,
                mostrarFocos = region.focos.isNotEmpty(),
                focos = parsearFocos(region.focos),
                mostrarEditarFocos = region.focos.isNotEmpty(),
                mostrarAsignarFocos = false,
                mostrarHabilidades = region.habilidades.isNotEmpty(),
                habilidades = parsearHabilidades(region.habilidades),
                mostrarEditarHabilidades = region.habilidades.isNotEmpty(),
                mostrarAsignarHabilidades = region.coberturada)
    }

    fun map(zona: ZonaRdd): FocosYHabilidadesPorUa {
        return FocosYHabilidadesPorUa(
                codigoUa = zona.codigo,
                personaId = zona.gerenteZona?.id,
                nombresPersona = WordUtils.capitalizeFully(zona.gerenteZona?.nombreApellido ?: ""),
                codigoCampania = zona.campania.nombreCorto,
                ua = zona,
                coberturada = zona.coberturada,
                mostrarFocos = zona.focos.isNotEmpty(),
                focos = parsearFocos(zona.focos),
                mostrarEditarFocos = zona.focos.isNotEmpty(),
                mostrarAsignarFocos = false,
                mostrarHabilidades = zona.habilidades.isNotEmpty(),
                habilidades = parsearHabilidades(zona.habilidades),
                mostrarEditarHabilidades = zona.habilidades.isNotEmpty(),
                mostrarAsignarHabilidades = zona.coberturada)
    }

    private fun parsearFocos(focos: MutableList<Foco>?): List<FocosYHabilidadesPorUa.FocoModel> {
        return focos?.map { parsearFoco(it) } ?: emptyList()
    }

    private fun parsearFoco(foco: Foco): FocosYHabilidadesPorUa.FocoModel {
        return FocosYHabilidadesPorUa.FocoModel(foco.id, foco.descripcion)
    }

    private fun parsearHabilidad(habilidad: Habilidad): FocosYHabilidadesPorUa.HabilidadModel {
        return FocosYHabilidadesPorUa.HabilidadModel(habilidad.id, habilidad.descripcion)
    }

    private fun parsearHabilidades(habilidades: MutableList<Habilidad>?): List<FocosYHabilidadesPorUa.HabilidadModel> {
        return habilidades?.map { parsearHabilidad(it) } ?: emptyList()
    }
}
