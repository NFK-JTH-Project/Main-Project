package com.example.nfk_project

import java.io.Serializable

class Teacher(
    val Signature: String,
    val Firstname: String,
    val Lastname: String,
    val Mobile: String,
    val Mail: String,
    val RoomName: String,
    val Photo: Boolean
): Serializable