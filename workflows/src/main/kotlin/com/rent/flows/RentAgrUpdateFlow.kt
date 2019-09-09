package com.rent.flows

import co.paralleluniverse.fibers.Suspendable
import com.rent.contracts.RentAgrContract
import com.rent.states.RentAgrState
import net.corda.core.contracts.Command
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********

    @InitiatingFlow
    @StartableByRPC
    class RentAgrUpdateFlow(val rentAgrState: RentAgrState): FlowLogic<SignedTransaction> (){

        /** The progress tracker provides checkpoints indicating the progress of the flow to observers. */
        override val progressTracker = ProgressTracker()

        @Suspendable
        override fun call():SignedTransaction {
            // Initiator flow logic goes here.

            //define notary
            val notary=serviceHub.networkMapCache.notaryIdentities.first()

            //list of signers
            val signers=rentAgrState.participants.map { it.owningKey }

            //command
            val createCommand=Command(RentAgrContract.Commands.Create(),signers)

            //inputState
            val queryCriteria = QueryCriteria.LinearStateQueryCriteria(externalId = listOf(rentAgrState.id))
            val inputStateAndRef = serviceHub.vaultService.queryBy(rentAgrState::class.java, queryCriteria).states.first()



            //builder with inputs and outputs
            val builder=TransactionBuilder(notary=notary)
                    .addCommand(createCommand)
                    .addOutputState(rentAgrState).addInputState(inputStateAndRef)

            //verify it (contract is called)
            builder.verify(serviceHub)

            //part signed flows
            val ptx=serviceHub.signInitialTransaction(builder)

            val sessions=(rentAgrState.participants-ourIdentity).map {  initiateFlow(it)}.toSet()

            val ftx=subFlow(CollectSignaturesFlow(ptx,sessions,CollectSignaturesFlow.tracker()))

            //finality flow
            return subFlow(FinalityFlow(ftx,sessions))

        }
    }

    @InitiatedBy(RentAgrUpdateFlow::class)
    class RentAgrUpdateFlowResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {

        @Suspendable
        override fun call() {
            val signTransactionFlow = object : SignTransactionFlow(counterpartySession) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {


                    // Responder rules for accepting?.

                    /*            val output = stx.tx.outputs.single().data
                                "This must be an IOU transaction." using (output is IOUState)
                                val iou = output as IOUState
                                "The IOU's value can't be too high." using (iou.value < 100)*/


                }
            }
            val expectedTxId = subFlow(signTransactionFlow).id

            //responder sub flow
            subFlow(ReceiveFinalityFlow(counterpartySession,expectedTxId))
        }
    }
