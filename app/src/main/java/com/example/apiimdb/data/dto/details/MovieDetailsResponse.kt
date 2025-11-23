package com.example.apiimdb.data.dto.details

import com.example.apiimdb.data.dto.Response
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("imDbRating") val imDbRating: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("countries") val countries: String?,
    @SerializedName("genres") val genres: String?,
    @SerializedName("directors") val directors: String?,
    @SerializedName("writers") val writers: String?,
    @SerializedName("stars") val stars: String?,
    @SerializedName("plot") val plot: String?
) : Response()