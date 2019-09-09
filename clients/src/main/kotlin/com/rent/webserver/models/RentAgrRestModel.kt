package com.rent.webserver.models

data class RentAgrRestModel(
        val id:String,
        val owner: Peer,
        //  val counterParty: Party,
        val members:MutableList<Peer>,
        val data: String="",
        val projectDeadline:String="",
        val legalDocLink:String=""
)