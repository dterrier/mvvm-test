package au.ziniestro.mvvmkotlintest.model

import com.google.gson.annotations.SerializedName

class RepoSearchResponse(
    @field:SerializedName("total_count")
    val total: Int = 0,
    @field:SerializedName("items")
    val items: ArrayList<Repo>
) {
    var nextPage: Int? = null
}