package au.ziniestro.mvvmkotlintest.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import au.ziniestro.mvvmkotlintest.R
import au.ziniestro.mvvmkotlintest.api.AppExecutors
import au.ziniestro.mvvmkotlintest.binding.FragmentDataBindingComponent
import au.ziniestro.mvvmkotlintest.databinding.FragmentRepoBinding
import au.ziniestro.mvvmkotlintest.di.Injectable
import au.ziniestro.mvvmkotlintest.ui.common.RetryCallback
import au.ziniestro.mvvmkotlintest.utils.autoCleared
import au.ziniestro.mvvmkotlintest.viewmodel.repo.RepoViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [RepoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val repoViewModel: RepoViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<FragmentRepoBinding>()

    private val params by navArgs<RepoFragmentArgs>()
    private var adapter by autoCleared<ContributorAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = DataBindingUtil.inflate<FragmentRepoBinding>(
            inflater,
            R.layout.fragment_repo,
            container,
            false
        )

        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding

        return dataBinding.root
    }

    private fun initContributorList(viewModel: RepoViewModel) {
        viewModel.contributors.observe(viewLifecycleOwner, { listResource ->
            if (listResource?.data != null) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val params: RepoFragmentArgs = RepoFragmentArgs.fromBundle(requireArguments())
        repoViewModel.setId(params.owner, params.name)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.repo = repoViewModel.repo

        val adapter = ContributorAdapter(dataBindingComponent, appExecutors) { contributor ->
            findNavController().navigate(
                RepoFragmentDirections.actionRepoFragmentToUserFragment(
                    contributor.avatarUrl,
                    contributor.login
                )
            )
        }

        this.adapter = adapter
        binding.contributorList.adapter = adapter
        initContributorList(repoViewModel)
    }
}