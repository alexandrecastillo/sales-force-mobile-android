package biz.belcorp.salesforce.modules.developmentpath.features.dashboard

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.model.SeccionAvanceModel
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosYHabilidadesPorUa
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel

class AvanceViewModel(val secciones: List<SeccionAvanceModel>)

class FocosGzViewModel(val secciones: List<SeccionFocoModel>)

class FocosHabilidadesPorUaViewModel(val uas: List<FocosYHabilidadesPorUa>)
