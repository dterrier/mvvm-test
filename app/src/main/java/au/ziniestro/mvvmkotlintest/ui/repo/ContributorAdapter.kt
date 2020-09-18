package au.ziniestro.mvvmkotlintest.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import au.ziniestro.mvvmkotlintest.R
import au.ziniestro.mvvmkotlintest.api.AppExecutors
import au.ziniestro.mvvmkotlintest.databinding.ContributorItemBinding
import au.ziniestro.mvvmkotlintest.model.Contributor
import au.ziniestro.mvvmkotlintest.ui.common.DataBoundListAdapter

class ContributorAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((Contributor) -> Unit)?
) : DataBoundListAdapter<Contributor, ContributorItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Contributor>() {
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl && oldItem.contributions == newItem.contributions
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ContributorItemBinding {
        val binding = DataBindingUtil.inflate<ContributorItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.contributor_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.contributor?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ContributorItemBinding, item: Contributor) {
        binding.contributor = item
    }

}