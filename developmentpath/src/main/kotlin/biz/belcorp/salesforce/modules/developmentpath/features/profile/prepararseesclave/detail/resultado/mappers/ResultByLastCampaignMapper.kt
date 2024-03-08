package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers

import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.core.utils.toPercentageNumber
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.resultado.ResultCampaign
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.validator.ResultDataTypeValidator
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.ContenedorInfoBasica
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Etiqueta
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Fila
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.model.Grupo

class ResultByLastCampaignMapper(private val textResolver: ResultLastCampaignTextResolver) {

    private val validate = ResultDataTypeValidator()

    fun map(result: ResultCampaign?): ContenedorInfoBasica {
        val entriesReEntries = createEntriesReEntries(result)
        val expensesActivity = createExpensesActivity(result)
        val possibleExpensesConsultant = createPossibleExpensesConsultant(result)
        val recovery = createRecovery(result)
        val activesRetention = createActivesRetention(result)

        return ContenedorInfoBasica(
            listOf(
                entriesReEntries,
                expensesActivity,
                possibleExpensesConsultant,
                recovery,
                activesRetention
            )
        )
    }

    private fun createEntriesReEntries(result: ResultCampaign?): Fila {
        val entries = createGroupEntries(result?.entries)
        val reentries = createGroupReEntries(result?.reentries)
        return Fila(listOf(entries, reentries))
    }

    private fun createExpensesActivity(result: ResultCampaign?): Fila {
        val expenses = createGroupExpenses(result?.expenses)
        val activity = createGroupActivity(result?.activity)
        return Fila(listOf(expenses, activity))
    }

    private fun createPossibleExpensesConsultant(result: ResultCampaign?): Fila {
        val possibleExpenses = createGroupPossibleExpenses(result?.possibleExpenses)
        val consultants6d6 = createConsultants6d6(result?.consultants6d6)
        return Fila(listOf(possibleExpenses, consultants6d6))
    }

    private fun createRecovery(result: ResultCampaign?): Fila {
        val recovery = createGroupRecovery(result?.recovery)
        val consultantsWithDebt = createGroupConsultantsWithDebt(result?.consultantsWithDebt)
        return Fila(listOf(recovery, consultantsWithDebt))
    }

    private fun createActivesRetention(result: ResultCampaign?): Fila {
        val activesRetention =
            createGroupActivesRetention(result?.campaign.orEmpty(), result?.activesRetention)
        return Fila(listOf(activesRetention))
    }

    private fun createGroupEntries(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_ingresos
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupReEntries(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_reingresos
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupExpenses(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_egresos
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupActivity(amount: Double?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_actividad
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.validatePercentage(amount?.toPercentageNumber())
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupPossibleExpenses(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_posibles_egresos
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.NEGATIVO,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createConsultants6d6(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_consultoras_6d6
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupRecovery(amount: Double?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_recuperacion
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.validatePercentage(amount?.toPercentageNumber())
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupConsultantsWithDebt(amount: Int?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            idRecurso = R.string.rst_consultoras_deuda
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.valueInt(amount)
        )
        return Grupo(titulo = title, subtitulo = value)
    }

    private fun createGroupActivesRetention(campaign: String, amount: Double?): Grupo {
        val title = Etiqueta(
            tipo = Etiqueta.Tipo.LIGERO,
            valor = textResolver.getActivesRetention(campaign.takeLastTwo())
        )
        val value = Etiqueta(
            tipo = Etiqueta.Tipo.FUERTE,
            valor = validate.validatePercentage(amount?.toPercentageNumber())
        )
        return Grupo(titulo = title, subtitulo = value)
    }

}
