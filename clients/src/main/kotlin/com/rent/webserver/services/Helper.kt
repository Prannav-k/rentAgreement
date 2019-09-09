package com.rent.webserver.services

import com.rent.webserver.models.Peer
import net.corda.core.identity.Party


class Helper{

    companion object {
        fun getPeerFromParty(party: Party?): Peer?{
            if (party != null)
                return Peer(party.name.organisation, party.name.locality, party.name.country)
            return null
        }


        fun getPeerListFromParty(partyL:List<Party>):MutableList<Peer>?{


            val peerL= mutableListOf<Peer>()

            partyL.forEach {
                peerL.add(getPeerFromParty(it)!!)
            }

            return peerL

        }

    }

}
