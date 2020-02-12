package edu.towson.cosc435.bajgai.todos_madhav.models

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("contents")
    val content: String,
    @SerializedName("completed")
    val isCompleted: Boolean,
    @SerializedName("image_url")
    val image: String,
    val dateCreated: String)