package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.invisible
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.virtualmethodology.R
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.ShareListener
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model.GroupsSegmentationModel
import biz.belcorp.salesforce.modules.virtualmethodology.features.utils.AnalyticUtils
import kotlinx.android.synthetic.main.item_methodology_view.view.*
import kotlinx.android.synthetic.main.item_viewpager_virtual_methodologyl.view.*

class VirtualMethodologyAdapter(
    private val items: List<GroupsSegmentationModel> = emptyList(),
    private val listener: ShareListener,
    private val onClick: (GroupsSegmentationModel) -> Unit
) : RecyclerView.Adapter<VirtualMethodologyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_methodology_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun updateView(model: GroupsSegmentationModel, images: List<String>) =
            with(itemView) {
                segmentIndicator?.isSelected = model.close

                if (model.close) {
                    if (images.isNotEmpty()) {
                        updateItemsView(itemView, images, model)
                    } else {
                        segmentIndicator?.setColorFilter(Color.TRANSPARENT)
                    }
                } else {
                    segmentIndicator?.setColorFilter(Color.WHITE)
                    itemPager?.gone()
                }

            }

        private fun updateItemsView(
            itemView: View,
            images: List<String>,
            model: GroupsSegmentationModel
        ) {
            with(itemView) {
                segmentIndicator?.setColorFilter(Color.TRANSPARENT)
                itemPager?.visible()

                if (images.size > 1) {
                    if (imgMetologiaVentas?.currentItem == 0) leftNav?.invisible()

                    setListener(leftNav, itemView, model, viewPager = imgMetologiaVentas)
                    setListener(rightNav, itemView, model, images, viewPager = imgMetologiaVentas)
                } else {
                    rightNav?.invisible()
                    leftNav?.invisible()
                }

                setListener(imgWhatsapp, itemView, viewPager = imgMetologiaVentas)
                setListener(imgFacebook, itemView, viewPager = imgMetologiaVentas)
                setListener(imgSMS, itemView, images = images, viewPager = imgMetologiaVentas)
            }
        }

        private fun setListener(
            view: View, itemView: View,
            model: GroupsSegmentationModel? = null,
            images: List<String> = emptyList(),
            viewPager: ViewPager? = null
        ) {
            with(itemView) {
                view.setOnClickListener {
                    when (it) {
                        leftNav -> it.leftNavListener(rightNav, model, viewPager)
                        rightNav -> it.rightNavListener(leftNav, model, images, viewPager)
                        imgWhatsapp -> whatsappListener(viewPager)
                        imgFacebook -> facebookListener(viewPager)
                        imgSMS -> smsListener(viewPager, images)
                        else -> Unit
                    }
                }
            }
        }

        private fun smsListener(
            viewPager: ViewPager?,
            images: List<String>
        ) {
            if (viewPager == null) return
            listener.clickOnSMS(images[viewPager.currentItem])
        }

        private fun facebookListener(viewPager: ViewPager?) {
            if (viewPager == null) return
            listener.clickOnFacebook(
                viewPager.getChildAt(
                    viewPager.currentItem
                ) as ImageView
            )
        }

        private fun whatsappListener(viewPager: ViewPager?) {
            if (viewPager == null) return
            listener.clickOnWhatsApp(
                viewPager.getChildAt(
                    viewPager.currentItem
                ) as ImageView
            )
        }

        private fun View.rightNavListener(
            left: View,
            model: GroupsSegmentationModel?,
            images: List<String>,
            viewPager: ViewPager? = null
        ) {
            if (viewPager == null) return
            model?.let { onClick.invoke(it) }
            viewPager.currentItem = viewPager.currentItem.plus(1)
            if (viewPager.currentItem == images.size - 1) {
                invisible()
                left.visible()
            } else {
                visible()
                left.visible()
            }
        }

        private fun View.leftNavListener(
            right: View,
            model: GroupsSegmentationModel?,
            viewPager: ViewPager? = null
        ) {
            if (viewPager == null) return
            model?.let { onClick.invoke(it) }
            viewPager.currentItem = viewPager.currentItem.minus(1)
            if (viewPager.currentItem == 0) {
                invisible()
                right.visible()
            } else
                visible()
            right.visible()
        }


        fun bind(model: GroupsSegmentationModel) = with(itemView) {

            val images = model.listSegm?.map { it.imagenMensaje } ?: listOf()
            val viewpager = VirtualMethodologyViewPagerAdapter(images)
            imgMetologiaVentas?.adapter = viewpager

            imgMetologiaVentas?.beginFakeDrag()

            groupName?.text = model.nombre
            groupContainer?.setOnClickListener {
                model.close = !model.close
                onClick.invoke(model)
                updateView(model, images)
                model.nombre?.let { AnalyticUtils.option(it) }
            }

            updateView(model, images)
        }
    }

}
