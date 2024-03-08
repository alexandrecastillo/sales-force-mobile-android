package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel

class CampaniaCampaniaModel(val tituloCampania: String,
                            val campania: String,
                            val acuerdos: List<AcuerdoModel>,
                            val mostrarCardHabilidades: Boolean,
                            val mostrarReconocerHabilidades: Boolean,
                            val mostrarHabilidades: Boolean,
                            val mostrarSinHabilidades: Boolean,
                            val cantidadHabilidadesReconocidas: String,
                            val porcentaje: Double,
                            val habilidadesReconocidas: List<HabilidadModel>)
