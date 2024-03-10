package com.example.entities

import kotlinx.serialization.Serializable

@Serializable
class UserActivity() {
    var userId = 0
    var action = ""
    var time = ""
}
//class UserActivity(userId: Int, action: String, time: String) {
//    var userId = 0
//    var action = ""
//    var time = ""
//}