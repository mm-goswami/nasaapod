package com.manmohan.nasaapod.data.api.model

class Response {
    private val results: List<Apod>? = null

    fun getResults(): List<Apod>? {
        return results
    }
}