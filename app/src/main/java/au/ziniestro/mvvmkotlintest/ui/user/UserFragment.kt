package au.ziniestro.mvvmkotlintest.ui.user

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
import au.ziniestro.mvvmkotlintest.R
import au.ziniestro.mvvmkotlintest.api.AppExecutors
import au.ziniestro.mvvmkotlintest.binding.FragmentDataBindingComponent
import au.ziniestro.mvvmkotlintest.databinding.FragmentUserBinding
import au.ziniestro.mvvmkotlintest.di.Injectable
import au.ziniestro.mvvmkotlintest.ui.common.RepoListAdapter
import au.ziniestro.mvvmkotlintest.utils.autoCleared
import au.ziniestro.mvvmkotlintest.viewmodel.user.UserViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<FragmentUserBinding>()

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val userViewModel: UserViewModel by viewModels {
        viewModelFactory
    }

    private var adapter by autoCleared<RepoListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val dataBinding = DataBindingUtil.inflate<FragmentUserBinding>(
            inflater,
            R.layout.fragment_user,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding

        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val params: UserFragmentArgs = UserFragmentArgs.fromBundle(requireArguments())
        userViewModel.setLogin(params.login)
        userViewModel.user.observe(viewLifecycleOwner, { userResource ->
            binding.user = userResource?.data
            binding.userResource = userResource
        })

        val rvAdapter = RepoListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors,
            showFullName = false
        ) { repo ->
            findNavController().navigate(
                UserFragmentDirections.actionUserFragmentToRepoFragment(
                    repo.name,
                    repo.owner.login
                )
            )
        }
        binding.repoList.adapter = rvAdapter
        this.adapter = rvAdapter
        initRepoList()
    }

    private fun initRepoList() {
        userViewModel.repositories.observe(viewLifecycleOwner, { repos ->
            adapter.submitList(repos?.data)
        })
    }
}