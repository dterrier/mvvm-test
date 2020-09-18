package au.ziniestro.mvvmkotlintest.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import au.ziniestro.mvvmkotlintest.api.Resource
import au.ziniestro.mvvmkotlintest.db.RepoRepo
import au.ziniestro.mvvmkotlintest.db.UserRepo
import au.ziniestro.mvvmkotlintest.model.Repo
import au.ziniestro.mvvmkotlintest.model.User
import au.ziniestro.mvvmkotlintest.utils.AbsentLiveData
import javax.inject.Inject

class UserViewModel
@Inject constructor(userRepo: UserRepo, repoRepo: RepoRepo) : ViewModel() {
    private val _login = MutableLiveData<String>()
    val login: LiveData<String>
        get() = _login

    val repositories: LiveData<Resource<List<Repo>>> = Transformations
        .switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create();
            } else {
                repoRepo.loadRepos(login)
            }
        }

    val user: LiveData<Resource<User>> = Transformations
        .switchMap(_login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                userRepo.loadUser(login)
            }
        }

    fun setLogin(login: String) {
        if (_login.value != login) {
            _login.value = login
        }
    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }
}