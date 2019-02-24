package com.mobiotics.demo.mobioticsapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RetroVideo(
        @field:SerializedName("description")
        var description: String?,
        @field:SerializedName("id")
        var id: Int?,
        @field:SerializedName("thumb")
        var thumb: String?,
        @field:SerializedName("title")
        var title: String?,
        @field:SerializedName("url")
        var url: String?,
        var status:String="1",
        var resumeFrom:String="0"

):Serializable