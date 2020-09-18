package au.ziniestro.mvvmkotlintest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import au.ziniestro.mvvmkotlintest.model.Contributor
import au.ziniestro.mvvmkotlintest.model.Repo
import au.ziniestro.mvvmkotlintest.model.RepoSearchResult
import au.ziniestro.mvvmkotlintest.model.User

@Database(
    entities = [
        User::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class
    ],
    version = 1
)
abstract class GithubDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}