package com.nicos.liveupdatenotification.firebase_service

import org.json.JSONObject

data class NotificationModel(
    var title: String? = null,
    var body: String? = null,
    val progress: Int? = null,
) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.optString("title"),
        jsonObject.optString("body"),
        jsonObject.optInt("progress"),
    )

    constructor() : this("", "", null)
}