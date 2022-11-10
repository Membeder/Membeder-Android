package com.heechan.membeder.ui.team.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.heechan.membeder.R
import com.heechan.membeder.base.BaseFragment
import com.heechan.membeder.databinding.FragmentTeamNameBinding

class TeamNameFragment : BaseFragment<FragmentTeamNameBinding>(R.layout.fragment_team_name) {
    val viewModel : TeamMakeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = viewModel

        binding.btnTeamMakeNameNext.setOnClickListener {
            (activity as TeamMakeActivity).gotoNext(1)
        }

        return binding.root
    }
}