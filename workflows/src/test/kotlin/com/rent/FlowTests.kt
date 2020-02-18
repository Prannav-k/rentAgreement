package com.rent

import com.rent.flows.RentAgrCreateFlow
import com.rent.flows.Responder
import com.rent.states.RentAgrState
import net.corda.core.identity.Party
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.internal.chooseIdentityAndCert
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkParameters
import net.corda.testing.node.TestCordapp
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FlowTests {
    private val network = MockNetwork(MockNetworkParameters(cordappsForAllNodes = listOf(
            TestCordapp.findCordapp("com.template.contracts"),
            TestCordapp.findCordapp("com.template.flows")
    )))
    private val ownerNode = network.createNode()
    private val party1Node = network.createNode()

    init {
        listOf(ownerNode, party1Node).forEach {
            it.registerInitiatedFlow(Responder::class.java)
        }
    }

    @Before
    fun setup() = network.runNetwork()

    @After
    fun tearDown() = network.stopNodes()

    @Test
    fun `dummy test`() {

    }


    @Test
    fun `create flow test`() {

        println("Executing Vaccine create flow")

        //idens
        val owner=ownerNode.info.chooseIdentityAndCert().party
        val party1=party1Node.info.chooseIdentityAndCert().party

        val members:MutableList<Party> = mutableListOf(party1)
        val vaccineState=RentAgrState(id = "123", owner = owner, members = members,agreementExpiryDate = "22nd oct",legalDocLink = "someURL")
        val createFlow=RentAgrCreateFlow(vaccineState)
        val future= ownerNode.startFlow(createFlow)

        network.runNetwork()

        val ftx: SignedTransaction = future.getOrThrow()
        ftx.verifyRequiredSignatures()
        val queryCriteria = QueryCriteria.LinearStateQueryCriteria(externalId = listOf(vaccineState.id))
        val queriedState=party1Node.services.vaultService.queryBy(RentAgrState::class.java,queryCriteria).states.first().state.data
        println("Queried State is "+queriedState.toString())

        assertEquals(vaccineState,queriedState)


    }


}