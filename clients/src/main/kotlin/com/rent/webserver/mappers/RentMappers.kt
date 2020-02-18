package com.rent.webserver.mappers

import com.rent.states.RentAgrState
import com.rent.webserver.models.RentAgrRestModel
import com.rent.webserver.services.Helper
import net.corda.core.identity.Party

class RentMappers{

    companion object {

        public fun getRentStateFromRest(rentAgrData: RentAgrRestModel, owner: Party, members : MutableList<Party>):RentAgrState{

/*
            val id:String,
            val owner: Party,
            val members:List<Party>,
            val data: String="",
            val agreementExpiryDate:String="",
            val legalDocLink:String="",*/

            return RentAgrState(
                 id=rentAgrData.id,
                 owner = owner,
                 members = members,
                 data = rentAgrData.data,
                 agreementExpiryDate = rentAgrData.projectDeadline,
                 legalDocLink = rentAgrData.legalDocLink
                 )


        }


        public fun getRentRestFromState(ss: RentAgrState):RentAgrRestModel{


            return RentAgrRestModel(

                    id = ss.id,
                    owner = Helper.getPeerFromParty(ss.owner)!!,
                    members = Helper.getPeerListFromParty(ss.members)!!,
                    data = ss.data,
                    projectDeadline = ss.agreementExpiryDate,
                    legalDocLink = ss.legalDocLink

            )

        }

    }

}