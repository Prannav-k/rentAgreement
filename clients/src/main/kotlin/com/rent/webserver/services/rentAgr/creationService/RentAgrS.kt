package com.rent.webserver.services.rentAgr.creationService

import com.rent.webserver.models.RentAgrRestModel


interface RentAgrS {

    fun createRentAgr(rentAgrRestModel : RentAgrRestModel):Any?
    fun updateRentAgr(rentAgrRestModel : RentAgrRestModel):Any?

}


