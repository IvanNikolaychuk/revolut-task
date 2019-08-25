package com.revolut.money.transfer.controller.transaction;

import com.revolut.money.transfer.app.transaction.TransactionRequestHandlerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactionRequest")
public class TransactionRequestController {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response newTransactionRequest() {
        String requestId = TransactionRequestHandlerFactory.create().createNewTransactionRequest();
        return Response.ok().entity(requestId).build();
    }

}
