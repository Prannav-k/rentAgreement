package com.rent.webserver.services.flow

import com.rent.flows.RentAgrCreateFlow
import com.rent.flows.RentAgrUpdateFlow
import com.rent.states.RentAgrState
import com.rent.webserver.NodeRPCConnection
import com.rent.webserver.models.Peer
import net.corda.core.contracts.StateAndRef
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CordaFlowUtils(var nodeRPCConnection: NodeRPCConnection){

    companion object {
        private val logger = LoggerFactory.getLogger(CordaFlowUtils::class.java)
    }



    private var proxy = nodeRPCConnection.proxy




    //rentAgr create flow

    //TODO invokeflow.returnvalue or other? behaviour

    fun invokeRentAgrCreateFlow(rentAgrState: RentAgrState):SignedTransaction{

        return nodeRPCConnection.proxy.startFlowDynamic(RentAgrCreateFlow::class.java,rentAgrState).returnValue.getOrThrow()

    }

    fun invokeRentAgrUpdateFlow(rentAgrState: RentAgrState):SignedTransaction{

        return nodeRPCConnection.proxy.startFlowDynamic(RentAgrUpdateFlow::class.java,rentAgrState).returnValue.getOrThrow()

    }

    /**
     * Query utils
     */


    fun getAllRentAgr():List<StateAndRef<RentAgrState>>{

        val res=nodeRPCConnection.proxy.vaultQuery(RentAgrState::class.java)
        return res.states

    }



    fun getRentAgrHistory(id:String):List<StateAndRef<RentAgrState>>{

        val queryCriteria1 = QueryCriteria.LinearStateQueryCriteria(externalId = listOf(id), status = Vault.StateStatus.ALL)
        val res = proxy.vaultQueryByCriteria(queryCriteria1, RentAgrState::class.java)

        return res.states


    }

    fun getNodeAddress(){

        val address=nodeRPCConnection.proxy.nodeInfo().addresses
        println("Node address is $address")
        logger.info("Node address is $address")

    }

    fun getPartyFromPeer(peer: Peer): Party?? {
        logger.info("Peer value is " + peer.toString())
        if (peer != null) {
            val party = nodeRPCConnection.proxy.wellKnownPartyFromX500Name(CordaX500Name(peer.o, peer.l, peer.c))
            logger.info(party.toString())
            if (party==null){
                logger.error("Error creating party name . Recheck party details")
                throw Exception("Error creating party name . Recheck party details")
            }
            return party
        }
        return null
    }


}
