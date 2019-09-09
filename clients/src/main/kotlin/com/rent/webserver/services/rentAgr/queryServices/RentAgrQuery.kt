package com.rent.webserver.services.rentAgr.queryServices

import com.rent.webserver.models.RentAgrRestModel

interface RentAgrQuery {

    fun getAllRentAgr():List<RentAgrRestModel>
    fun getRentHistoryById(id:String): List<RentAgrRestModel>

}