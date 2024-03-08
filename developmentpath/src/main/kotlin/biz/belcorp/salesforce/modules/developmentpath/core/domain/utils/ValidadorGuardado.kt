package biz.belcorp.salesforce.modules.developmentpath.core.domain.utils

class ValidadorGuardado<K, J>(private val seleccionadorK: Seleccionador<K>,
                              private val seleccionadorJ: Seleccionador<J>) {

    fun validar(): Boolean {
        return seleccionadorK.seleccionados.isNotEmpty() &&
            seleccionadorJ.seleccionados.isNotEmpty()
    }

    fun validarFocosPropios(): Boolean {
        return seleccionadorK.seleccionados.isNotEmpty()
    }
}
