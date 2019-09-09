package com.rent.webserver.controllers


import com.rent.webserver.NodeRPCConnection
import com.rent.webserver.models.ResponseMsg
import com.rent.webserver.models.RentAgrRestModel
import com.rent.webserver.services.flow.CordaFlowUtils
import com.rent.webserver.services.rentAgr.creationService.RentAgrImpl
import com.rent.webserver.services.rentAgr.queryServices.RentAgrQueryImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Define CorDapp-specific endpoints in a controller such as this.
 */
@RestController
@RequestMapping() // The paths for GET and POST requests are relative to this base path.
class RentController(rpc: NodeRPCConnection) {

    private val logger = LoggerFactory.getLogger(RestController::class.java)


    @Autowired
    val cordaFlowUtils: CordaFlowUtils = CordaFlowUtils(nodeRPCConnection = rpc)


    @Autowired
    private val rentService= RentAgrImpl(rpc)

    @Autowired
    private val rentQueryImpl=RentAgrQueryImpl(rpc)

    private val proxy = rpc.proxy

    @GetMapping(value = "/customendpoint", produces = arrayOf("text/plain"))
    private fun status() = "Modify this."


    @GetMapping(value = "/address")
    private fun getAddress() {
        logger.info("In controller")
        return cordaFlowUtils.getNodeAddress()
    }

    @PostMapping("/rentAgr")
    private fun createRentAgr(@RequestBody s : RentAgrRestModel):ResponseEntity<ResponseMsg>{

        try{
            rentService.createRentAgr(s)
            val responseMsg = ResponseMsg(200, " OK", "Good Response")
            return ResponseEntity.status(200).body(responseMsg)
        } catch (ex: Exception) {
            logger.error(s.id , ex)
            val responseMsg = ResponseMsg(400, " error", ex.message!!)
            return ResponseEntity.status(400).body(responseMsg)
        }
    }


    @PutMapping("/rentAgr")
    private fun updateRentAgr(@RequestBody s : RentAgrRestModel):ResponseEntity<ResponseMsg>{

        try{
            rentService.updateRentAgr(s)
            val responseMsg = ResponseMsg(200, " OK", "Good Response")
            return ResponseEntity.status(200).body(responseMsg)
        } catch (ex: Exception) {
            logger.error(s.id , ex)
            val responseMsg = ResponseMsg(400, " error", ex.message!!)
            return ResponseEntity.status(400).body(responseMsg)
        }
    }

    @GetMapping("/rentAgr")
    private fun getRentAgr():Any{
        try{
            return rentQueryImpl.getAllRentAgr()
        } catch (ex: Exception) {
            println(ex.message)
            val responseMsg = ResponseMsg(400, " error", ex.message!!)
            return ResponseEntity.status(400).body(responseMsg)
        }

    }

    @GetMapping("/rentAgr/history")
    private fun getRentAgrHistory(@RequestParam id:String):Any?{
        try{
            return rentQueryImpl.getRentHistoryById(id)
        } catch (ex: Exception) {
            logger.error(ex.message.toString())
            val responseMsg = ResponseMsg(400, " error", ex.message!!)
            return ResponseEntity.status(400).body(responseMsg)
        }
    }

}

