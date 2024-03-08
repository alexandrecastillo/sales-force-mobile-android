package biz.belcorp.salesforce.base.features.home.user

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.visible
import kotlinx.android.synthetic.main.fragment_user_info_nav.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserInfoNavFragment : BaseFragment() {

    private val viewModel by viewModel<UserViewModel>()

    override fun getLayout() = R.layout.fragment_user_info_nav

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun loadUserInformation(user: UserModel) {
        userName?.text = user.name
        if (user.hasSegment) {
            userSegmentContainer?.visible()
            imageAvatar?.setBorderColor(ContextCompat.getColor(requireContext(), user.colorSegment))
            userSegmentIndicator?.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    user.colorSegment
                )
            )
            userSegment?.text = user.level
        }
        buttonProfile?.paintFlags = buttonProfile.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        buttonProfile?.setOnClickListener {

        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, userViewStateObserver)
        viewModel.getUserInformation()
    }

    private val userViewStateObserver = Observer<UserInfoViewState> { userSate ->
        userSate?.let {
            when (it) {
                is UserInfoViewState.Success -> loadUserInformation(it.user)
            }
        }
    }

    companion object {
        fun newInstance(): UserInfoNavFragment {
            return UserInfoNavFragment()
        }
    }
}
