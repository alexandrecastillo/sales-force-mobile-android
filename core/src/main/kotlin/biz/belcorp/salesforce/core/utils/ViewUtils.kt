package biz.belcorp.salesforce.core.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.SystemClock
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import biz.belcorp.salesforce.core.constants.Constant
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.whileSelect

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View?.toggleVisibility() {
    if (this == null) return
    if (isVisible) gone() else visible()
}

fun View.dismissKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setOnOneClickListener(action: (view: View) -> Unit) {
    val oneClickListener = object : OnOneClickListener() {
        override fun onOneCLick(view: View) {
            action.invoke(view)
        }
    }

    this.setOnClickListener(oneClickListener)
}

abstract class OnOneClickListener : View.OnClickListener {

    companion object {
        private const val DELAY = 1000L
    }

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < DELAY) {
            return
        }

        lastClickTime = SystemClock.elapsedRealtime()

        onOneCLick(view)
    }

    abstract fun onOneCLick(view: View)
}

fun ViewPager.previousPage() {
    if (currentItem == 0) currentItem = adapter?.count ?: 0
    else currentItem -= 1
}

fun ViewPager2.previousPage() {
    if (currentItem == 0) currentItem = adapter?.itemCount ?: 0
    else currentItem -= 1
}

fun ViewPager2.nextPage() {
    if (currentItem + 1 == adapter?.itemCount) currentItem = 0
    else currentItem += 1
}

fun ViewPager.nextPage() {
    if (currentItem + 1 == adapter?.count) currentItem = 0
    else currentItem += 1
}

fun ViewPager2?.lastIndex(): Int {
    return (this?.adapter?.itemCount ?: Constant.NUMBER_ONE) - Constant.NUMBER_ONE
}

fun ViewPager2.onPageSelected(f: (position: Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            f.invoke(position)
        }
    })
}

fun TabLayout.setupWithViewPager(viewPager: ViewPager2) {
    TabLayoutMediator(this, viewPager) { _, _ -> }.attach()
}

/** Módulo para convertir una vista a bitmap */
fun View.toBitmap(context: Context): Bitmap {
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    with(this) {
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        buildDrawingCache()

        val bitmap = Bitmap.createBitmap(
            this.measuredWidth, this.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}

@Suppress("DEPRECATION")
fun TextView.setHtmlText(html: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

fun Spinner.posicionarDialogoDebajo() {
    val spinner = this
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            spinner.dropDownVerticalOffset =
                spinner.dropDownVerticalOffset +
                    spinner.height

            spinner.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}

fun EditText.onTextChanged(): ReceiveChannel<String> =
    Channel<String>(capacity = Channel.UNLIMITED).also { channel ->
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.toString().orEmpty().let(channel::offer)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        })
    }

fun <T> ReceiveChannel<T>.debounce(timeMilliSeconds: Long): ReceiveChannel<T> =
    Channel<T>(capacity = Channel.CONFLATED).also { channel ->
        GlobalScope.launch(context = Dispatchers.Main) {
            var value = receive()
            whileSelect {
                onTimeout(timeMilliSeconds) {
                    channel.offer(value)
                    value = receive()
                    true
                }
                onReceive {
                    value = it
                    true
                }
            }
        }
    }

fun CompoundButton.checked() {
    isChecked = true
}

fun CompoundButton.unchecked() {
    isChecked = false
}

fun SwipeRefreshLayout.setIndicatorColor(@ColorRes colorRes: Int) {
    setColorSchemeColors(context.getCompatColor(colorRes))
}

fun SwipeRefreshLayout.setOnSafeRefreshListener(action: () -> Unit) {
    val refreshListener = object : OnSafeRefreshListener() {
        override fun onRejectedRefresh() {
            isRefreshing = false
        }

        override fun onSafeRefresh() {
            action.invoke()
        }
    }

    this.setOnRefreshListener(refreshListener)
}

abstract class OnSafeRefreshListener : SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val DELAY = 15000L
    }

    private var lastClickTime = 0L

    override fun onRefresh() {
        if (SystemClock.elapsedRealtime() - lastClickTime < DELAY) {
            onRejectedRefresh()
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        onSafeRefresh()
    }

    abstract fun onRejectedRefresh()

    abstract fun onSafeRefresh()
}
