package com.example.logoquizgame.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LogoData {
    @SerializedName("imgUrl")
    @Expose
    var imgUrl: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

}