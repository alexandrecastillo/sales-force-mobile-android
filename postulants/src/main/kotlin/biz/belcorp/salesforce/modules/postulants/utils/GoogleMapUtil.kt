package biz.belcorp.salesforce.modules.postulants.utils

import android.view.View
import android.widget.RelativeLayout
import com.google.android.gms.maps.MapView

fun MapView.setButtonsBottomEnd() {
    findViewById<View>(Integer.parseInt("1"))?.also { view1 ->
        (view1.parent as? View)?.findViewById<View>(Integer.parseInt("2"))?.also { view2 ->
            val rlp = view2.layoutParams as RelativeLayout.LayoutParams
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            rlp.setMargins(0, 0, 180, 30)
        }
    }
}
