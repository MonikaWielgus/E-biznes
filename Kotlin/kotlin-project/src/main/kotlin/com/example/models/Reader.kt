package com.example.models
import kotlinx.serialization.Serializable

@Serializable
data class Reader(val nick: String, val firstName: String, val lastName: String, val email: String)

val readerStorage = mutableListOf(Reader("jan_nowak", "jan", "nowak", "jannowak@gmail.com"),
    Reader("olaola", "aleksandra", "mazur", "olaola@gmail.com"))