package com.example.features.getUserActivity

import com.example.entities.UserActivity
import kotlinx.serialization.Serializable


@Serializable
data class ActivityModelResponse(val activity: List<UserActivity>)