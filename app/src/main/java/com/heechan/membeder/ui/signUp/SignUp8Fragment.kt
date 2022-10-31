package com.heechan.membeder.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.heechan.membeder.R
import com.heechan.membeder.base.BaseFragment
import com.heechan.membeder.databinding.FragmentSignUp4Binding
import com.heechan.membeder.databinding.FragmentSignUp6Binding
import com.heechan.membeder.databinding.FragmentSignUp7Binding
import com.heechan.membeder.databinding.FragmentSignUp8Binding

class SignUp8Fragment : SignUpFragment<FragmentSignUp8Binding>(R.layout.fragment_sign_up_8) {
    val viewModel : SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        binding.next.setOnClickListener {
            gotoNext()
        }
    }

    override val currentPage: Int = 8
}