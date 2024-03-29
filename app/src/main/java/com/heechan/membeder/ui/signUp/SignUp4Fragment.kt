package com.heechan.membeder.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.heechan.membeder.R
import com.heechan.membeder.base.BaseFragment
import com.heechan.membeder.databinding.FragmentSignUp4Binding

class SignUp4Fragment : SignUpFragment<FragmentSignUp4Binding>(R.layout.fragment_sign_up_4) {
    val viewModel : SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.currentPage = currentPage

        binding.hdSingUp4.setNavigationClickListener {
            gotoPrev()
        }

        binding.next.setOnClickListener {
            hideKeyboard()
            gotoNext()
        }
    }

    override val currentPage: Int = 4
}