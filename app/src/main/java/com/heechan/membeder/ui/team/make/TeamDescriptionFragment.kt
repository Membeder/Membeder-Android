package com.heechan.membeder.ui.team.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.heechan.membeder.R
import com.heechan.membeder.base.BaseFragment
import com.heechan.membeder.databinding.FragmentTeamDescriptionBinding

class TeamDescriptionFragment : BaseFragment<FragmentTeamDescriptionBinding>(R.layout.fragment_team_description) {
    val viewModel : TeamMakeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.errTeamDescription.errorMessasge = it ?: ""
            if(it != null) binding.errTeamDescription.visibility = View.VISIBLE
        }


        binding.btnTeamMakeDescriptionNext.setOnClickListener {
            if (viewModel.inputCheckDescription()) {
                viewModel.errorMessage.value = null
                (activity as TeamMakeActivity).gotoNext(2)
            }
        }

        return binding.root
    }
}