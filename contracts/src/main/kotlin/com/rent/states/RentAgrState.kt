package com.rent.states

import com.rent.contracts.RentAgrContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import java.util.*

// *********
// * State *
// *********
@BelongsToContract(RentAgrContract::class)
data class RentAgrState(
        val id:String,
        val owner:Party,
        val members:MutableList<Party>,
        val data: String="",
        val projectDeadline:String="",
        val legalDocLink:String="",
        override val participants: List<Party> = members,
        override val linearId: UniqueIdentifier= UniqueIdentifier(id, UUID.randomUUID())
        ) : ContractState , LinearState



