package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import biz.belcorp.salesforce.modules.virtualmethodology.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_images_viewpager.view.*

class VirtualMethodologyViewPagerAdapter(private val items: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, objectView: Any): Boolean = view == objectView

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_images_viewpager, container, false)

        Glide.with(container.context)
            .load(items[position])
            .into(view.imageShare)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, objectView: Any) {
        container.removeView(objectView as View)
    }
}
