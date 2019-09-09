package com.rent.webserver.services.rentAgr.creationService

import com.rent.webserver.NodeRPCConnection
import com.rent.webserver.models.RentAgrRestModel
import com.rent.webserver.services.flow.CordaFlowUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RentAgrImpl(var nodeRPCConnection: NodeRPCConnection) : RentAgrS {

    @Autowired
    val cordaFlowUtils= CordaFlowUtils(nodeRPCConnection)

    override fun createRentAgr(rentAgrRestModel : RentAgrRestModel):Any?{

        val rentBuilder= RentAgrBuilder(nodeRPCConnection)
        val rentAgrState=rentBuilder.buildRentAgrState(rentAgrRestModel)
        val signedTransaction=cordaFlowUtils.invokeRentAgrCreateFlow(rentAgrState)

        return signedTransaction
    }

    override fun updateRentAgr(rentAgrRestModel: RentAgrRestModel): Any? {
        val rentBuilder= RentAgrBuilder(nodeRPCConnection)
        val rentAgrState=rentBuilder.buildRentAgrState(rentAgrRestModel)
        val signedTransaction=cordaFlowUtils.invokeRentAgrUpdateFlow(rentAgrState)

        return signedTransaction
    }
}