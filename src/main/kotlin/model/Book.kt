package model

import kotlinx.serialization.Serializable

@Serializable
data class Book(val id: Int? = null, val title: String, val author: String)