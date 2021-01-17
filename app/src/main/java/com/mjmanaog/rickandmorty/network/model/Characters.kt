package com.mjmanaog.rickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("results") val result: List<Character>
){
    data class Character(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("species") val species: String,
        @SerializedName("status") val status: String,
        @SerializedName("image") val image: String,
        @SerializedName("origin") val origin: Origin,
    ){
        data class Origin(@SerializedName("name") val originName: String)
    }
}