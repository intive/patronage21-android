package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.repository.domain.model.GroupParcelable
import com.intive.tech_groups.presentation.screens.GroupDetailsScreen
import com.intive.tech_groups.presentation.viewmodels.GroupDetailsViewModel
import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import com.intive.ui.PatronativeTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TAG = "GroupDetailsFragment"

class GroupDetailsFragment : Fragment(){

    private val groupDetailsViewModel: GroupDetailsViewModel by viewModel<GroupDetailsViewModel>()
    private val stageViewModel by sharedViewModel<StageViewModel>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {


            val safeArgs: GroupDetailsFragmentArgs by navArgs()
            groupDetailsViewModel.selectedGroup = safeArgs.groupParcelable!!

            groupDetailsViewModel.getStages(groupDetailsViewModel.selectedGroup.id)

            setContent {
                PatronativeTheme {
                    GroupDetailsScreen(
                        groupDetailsViewModel,
                        stageViewModel::getStageDetails,
                        groupDetailsViewModel::onGetStagesRetryClick,
                        findNavController()
                    )
                }
            }
        }
    }
}