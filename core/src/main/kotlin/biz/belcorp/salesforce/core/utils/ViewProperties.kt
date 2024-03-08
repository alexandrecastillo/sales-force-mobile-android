package biz.belcorp.salesforce.core.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import biz.belcorp.mobile.components.core.extensions.getColor

private const val NO_GETTER: String = "Property does not have a getter"

var TextView.textResource: Int
    get() = throw RuntimeException(NO_GETTER)
    set(v) = setText(v)

var TextView.textColor: Int
    get() = throw RuntimeException(NO_GETTER)
    set(value) = setTextColor(value)

var TextView.textColorResource: Int
    get() = throw RuntimeException(NO_GETTER)
    set(colorId) = setTextColor(getColor(colorId))

var ImageView.imageResource: Int
    get() = throw RuntimeException(NO_GETTER)
    set(v) = setImageResource(v)

var ImageView.image: Drawable?
    inline get() = drawable
    inline set(value) = setImageDrawable(value)

var View.backgroundResource: Int
    get() = throw RuntimeException(NO_GETTER)
    set(v) = setBackgroundResource(v)

var View.backgroundDrawable: Drawable?
    inline get() = background
    set(value) { background = value }

var View.backgroundColor: Int
    get() = throw RuntimeException(NO_GETTER)
    set(v) = setBackgroundColor(v)

var View.backgroundColorResource: Int
    get() = throw RuntimeException(NO_GETTER)
    set(colorId) = setBackgroundColor(getColor(colorId))

var View.verticalPadding: Int
    get() = throw RuntimeException(NO_GETTER)
    set(value) = setPadding(paddingLeft, value, paddingRight, value)

var View.horizontalPadding: Int
    get() = throw RuntimeException(NO_GETTER)
    set(value) = setPadding(value, paddingTop, value, paddingBottom)
