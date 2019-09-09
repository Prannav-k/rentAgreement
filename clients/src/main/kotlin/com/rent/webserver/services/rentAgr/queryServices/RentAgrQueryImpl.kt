package com.rent.webserver.services.rentAgr.queryServices

import com.rent.webserver.NodeRPCConnection
import com.rent.webserver.mappers.RentMappers
import com.rent.webserver.models.RentAgrRestModel
import com.rent.webserver.services.flow.CordaFlowUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RentAgrQueryImpl(nodeRPCConnection: NodeRPCConnection):RentAgrQuery {

    @Autowired
    val cordaFlowUtils=CordaFlowUtils(nodeRPCConnection)

    override fun getAllRentAgr(): List<RentAgrRestModel> {

        val stateRefList=cordaFlowUtils.getAllRentAgr()
        var RentAgrRestModelList=ArrayList<RentAgrRestModel>()

        stateRefList.forEach {
            RentAgrRestModelList.add(RentMappers.getRentRestFromState(it.state.data))
        }

        return RentAgrRestModelList
    }


    override fun getRentHistoryById(id:String): List<RentAgrRestModel> {

        val stateRefList=cordaFlowUtils.getRentAgrHistory(id)
        var RentAgrRestModelList=ArrayList<RentAgrRestModel>()

        stateRefList.forEach {
            RentAgrRestModelList.add(RentMappers.getRentRestFromState(it.state.data))
        }

        return RentAgrRestModelList
    }


}