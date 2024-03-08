package biz.belcorp.salesforce.core.utils

fun consume(f: () -> Unit): Boolean {
    f.invoke()
    return true
}


fun String?.notEquals(valor: String) = !this.equals(valor, true)
