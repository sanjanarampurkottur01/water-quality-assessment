package com.example.b00957180.model

import com.google.gson.annotations.SerializedName

data class Parameter (
    var name: String,
    var result: String,
    var limit: String
)

data class ParameterDataResponse(
    @SerializedName("columns") val columns: List<String>,
    @SerializedName("data") val data: List<List<String>>
)