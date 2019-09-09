package com.rent.webserver.services.rentAgr.creationService

import com.rent.states.RentAgrState
import com.rent.webserver.NodeRPCConnection
import com.rent.webserver.mappers.RentMappers
import com.rent.webserver.models.RentAgrRestModel
import com.rent.webserver.services.flow.CordaFlowUtils
import net.corda.core.identity.Party
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

class RentAgrBuilder (var nodeRPCConnection:NodeRPCConnection){

    val logger=LoggerFactory.getLogger(RentAgrBuilder::class.java)

    @Autowired
    val cutils= CordaFlowUtils(nodeRPCConnection)

    val rentMappers= RentMappers()

    fun buildRentAgrState(rentAgrData: RentAgrRestModel):RentAgrState{

        logger.info("rest model in builder is is ${rentAgrData.toString()}")
        var owner = cutils.getPartyFromPeer(rentAgrData.owner)

        logger.info("owner is ${owner.toString()}")
        var members: MutableList<Party> = mutableListOf()
        rentAgrData.members.forEach{
            members.add(cutils.getPartyFromPeer(it)!!)
        }
        logger.info("members are ${members.toString()}")
        return RentMappers.getRentStateFromRest(rentAgrData=rentAgrData,owner = owner!!,members = members)
    }

}