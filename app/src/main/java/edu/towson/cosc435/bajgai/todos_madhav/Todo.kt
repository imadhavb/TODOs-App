package edu.towson.cosc435.bajgai.todos_madhav

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("image_url")
    val imageURL: String)

