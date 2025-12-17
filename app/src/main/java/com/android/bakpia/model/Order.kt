package com.android.bakpia.model

data class Order(
    var userId: String? = null,
    var total: Int = 0,
    var status: String? = null
)

