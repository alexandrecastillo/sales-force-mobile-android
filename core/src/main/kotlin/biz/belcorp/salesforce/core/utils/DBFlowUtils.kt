package biz.belcorp.salesforce.core.utils

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.SQLOperator
import com.raizlabs.android.dbflow.sql.language.property.Property
import com.raizlabs.android.dbflow.structure.BaseModel


fun List<BaseModel>.actualizar() = this.forEach { it.save() }

inline fun <reified T : BaseModel> List<T>.update() {
    FlowManager
        .getModelAdapter(T::class.java)
        .updateAll(this)
}

inline fun <reified T : BaseModel> List<T>.insert() {
    FlowManager
        .getModelAdapter(T::class.java)
        .insertAll(this)
}

inline fun <reified T : BaseModel> List<T>.insertOneByOne() {
    forEach {
        try {
            it.insert()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

inline fun <reified T : BaseModel> List<T>.save() {
    FlowManager
        .getModelAdapter(T::class.java)
        .saveAll(this)
}

inline fun <reified T : BaseModel> List<T>?.deleteAndInsert() {
    Delete().from(T::class.java).executeUpdateDelete()

    this?.insert()
}

inline fun <reified T : BaseModel> List<T>.deleteAndInsertIfNotEmpty() {
    if (isNotEmpty()) {
        Delete().from(T::class.java).executeUpdateDelete()
        this.insert()
    }
}

inline fun <reified T : BaseModel> T.deleteAndInsert() {
    Delete().from(T::class.java).executeUpdateDelete()
    this.insert()
}

inline fun <reified T : BaseModel> T.deleteAll() {
    Delete().from(T::class.java).executeUpdateDelete()
}

infix fun <T> Property<T>.eqNullable(nullable: T?): SQLOperator {
    return if (nullable != null) this.eq(nullable)
    else this.isNull
}
