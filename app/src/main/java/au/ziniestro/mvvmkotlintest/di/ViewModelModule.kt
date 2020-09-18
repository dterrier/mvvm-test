package au.ziniestro.mvvmkotlintest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.ziniestro.mvvmkotlintest.viewmodel.GithubViewModelFactory
import au.ziniestro.mvvmkotlintest.viewmodel.repo.RepoViewModel
import au.ziniestro.mvvmkotlintest.viewmodel.search.SearchViewModel
import au.ziniestro.mvvmkotlintest.viewmodel.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}