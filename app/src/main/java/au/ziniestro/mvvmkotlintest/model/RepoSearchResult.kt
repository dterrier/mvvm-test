package au.ziniestro.mvvmkotlintest.model

import androidx.room.Entity
import androidx.room.TypeConverters
import au.ziniestro.mvvmkotlintest.db.GithubTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(GithubTypeConverters::class)
class RepoSearchResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)