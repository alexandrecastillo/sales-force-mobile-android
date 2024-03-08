package biz.belcorp.salesforce.components.commons.pager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import biz.belcorp.salesforce.components.widgets.viewpager.HeightWrappingViewPager

class PagesFragmentAdapter(
    fm: FragmentManager, private val pages: PagesBuilder = PagesBuilder()
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mCurrentPosition = -1

    override fun getPageTitle(position: Int) = pages.get(position).title

    override fun getItem(position: Int) = pages.get(position).fragment

    override fun getItemPosition(obj: Any) = PagerAdapter.POSITION_NONE

    override fun getCount() = pages.size()

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)
        if (position != mCurrentPosition) {
            val pager = container as? HeightWrappingViewPager
            (obj as? Fragment)?.view?.apply {
                mCurrentPosition = position
                pager?.measureCurrentView(this)
            }
        }
    }

    fun update(pages: PagesBuilder) {
        this.pages.clear()
        this.pages.addAll(pages)
        notifyDataSetChanged()
    }
}

class PagesBuilder(
    private val pages: MutableList<Page> = mutableListOf()
) : Iterable<PagesBuilder.Page> {

    override fun iterator() = pages.iterator()

    fun add(page: Page) {
        pages.add(page)
    }

    fun addAll(data: List<Page>) {
        pages.addAll(data)
    }

    fun addAll(data: PagesBuilder) {
        pages.addAll(data.pages)
    }

    fun get(index: Int): Page = pages[index]

    fun size(): Int = pages.size

    fun clear() {
        pages.clear()
    }

    class Page(val title: String? = null, val fragment: Fragment)
}
