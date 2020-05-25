package com.example.nfk_project
import java.io.Serializable
class Room(
    val Name: String

): Serializable{
    override fun toString(): String {
        return this.Name
    }
}