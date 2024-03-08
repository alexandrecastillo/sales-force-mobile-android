package biz.belcorp.salesforce.base.features.home.user

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.base.R
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.*
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfoFragment : BaseFragment() {

    private val viewModel by inject<UserViewModel>()

    override fun getLayout() = R.layout.fragment_user_info

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun loadUserInformation(model: UserModel) {
        userEmoji?.text = createEmoji(getString(R.string.temp_emoji))
        userName?.text = String.format(getString(R.string.welcome_user_name), model.name)
        userDescription?.text = model.description
        if (model.hasSegment) {
            userSegmentIndicator?.setColorFilter(
                ContextCompat.getColor(requireContext(), model.colorSegment)
            )
            userSegment?.text = model.level
            userSegmentContainer?.visible()
        }
        rotateUserEmoji()
    }

    private fun rotateUserEmoji() {
        userEmoji?.rotateAnimation(
            fromDegrees = FROM_DEGREES,
            toDegrees = TO_DEGREES,
            repeatMode = Animation.REVERSE
        )
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
        fun newInstance(): UserInfoFragment {
            return UserInfoFragment()
        }
    }
}
