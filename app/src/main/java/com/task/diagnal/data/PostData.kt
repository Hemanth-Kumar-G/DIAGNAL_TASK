package com.task.diagnal.data


import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("page")
    var page: Page
) {
    data class Page(
        @SerializedName("content-items")
        var contentItems: ContentItems,
        @SerializedName("page-num")
        var pageNum: String,
        @SerializedName("page-size")
        var pageSize: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("total-content-items")
        var totalContentItems: String
    ) {
        data class ContentItems(
            @SerializedName("content")
            var content: List<Content>
        ) {
            data class Content(
                @SerializedName("name")
                var name: String,
                @SerializedName("poster-image")
                var posterImage: String
            )
        }
    }
}