package au.ziniestro.mvvmkotlintest.di

import au.ziniestro.mvvmkotlintest.ui.repo.RepoFragment
import au.ziniestro.mvvmkotlintest.ui.search.SearchFragment
import au.ziniestro.mvvmkotlintest.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}