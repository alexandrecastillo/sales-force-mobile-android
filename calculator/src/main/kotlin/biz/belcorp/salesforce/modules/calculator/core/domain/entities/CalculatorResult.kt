package biz.belcorp.salesforce.modules.calculator.core.domain.entities

data class CalculatorResult(val codResultado: Int?,
                            val codRegion: String?,
                            val codZona: String?,
                            val codSeccion: String?,
                            val campania: String?,
                            val valorResultado: Double?,
                            val exitoso: Boolean?,
                            val bono: Double?,
                            val detalleResultadoCalculadora: List<CalculatingResultDetail>)
