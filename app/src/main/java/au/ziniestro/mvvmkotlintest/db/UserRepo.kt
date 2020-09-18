package au.ziniestro.mvvmkotlintest.db

import androidx.lifecycle.LiveData
import au.ziniestro.mvvmkotlintest.api.*
import au.ziniestro.mvvmkotlintest.model.User
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val githubApi: GithubApi
) {
    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.findUserById(login)
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return githubApi.getUser(login)
            }
        }.asLiveData()
    }
}