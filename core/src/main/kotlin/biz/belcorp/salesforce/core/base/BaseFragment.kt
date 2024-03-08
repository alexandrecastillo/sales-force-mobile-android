package biz.belcorp.salesforce.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.utils.inflate
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseFragment : Fragment() {

    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(inflater, getLayout())
    }

    fun isAttached() = activity != null && isAdded

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SplitCompat.install(requireContext())
    }

    fun toast(string: String) {
        Toast.makeText(activity, string, Toast.LENGTH_LONG).show()
    }
}
