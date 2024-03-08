package biz.belcorp.salesforce.modules.developmentpath.core.data.utils

fun <K, J> Triple<List<K>, List<K>, List<K>>.map(f: (K) -> J?): Triple<List<J>, List<J>, List<J>> {

    val first = this.first.mapNotNull { f.invoke(it) }
    val second = this.second.mapNotNull { f.invoke(it) }
    val third = this.third.mapNotNull { f.invoke(it) }

    return Triple(first, second, third)
}

fun <K, J> List<K>.mapNotNull(f: (K) -> J?): List<J> {
    return this.map { f.invoke(it) }.filter { it != null }.map { it!! }
}
