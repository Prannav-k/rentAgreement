package com.rent.webserver.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseMsg(
        @JsonProperty("code")
        val code: Int,
        @JsonProperty("status")
        val status: String,
        @JsonProperty("message")
        val message: String)