package com.example.models
import kotlinx.serialization.Serializable

@Serializable
data class Book(val title: String, val author: Author)

@Serializable
data class Author(val firstName: String, val lastName: String, val nationality: String)

val bookStorage = mutableListOf(Book("Gone with the wind", Author("Margaret", "Mitchell", "American")),
    Book("Wuthering Heights", Author("Emily", "Bronte", "English")))

val authorStorage = mutableListOf(Author("Margaret", "Mitchell", "American"),
    Author("Emily", "Bronte", "English"))