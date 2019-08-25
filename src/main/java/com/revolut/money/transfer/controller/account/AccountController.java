package com.revolut.money.transfer.controller.account;

import com.revolut.money.transfer.app.account.AccountBalanceHandler;
import com.revolut.money.transfer.app.account.AccountBalanceHandlerFactory;
import com.revolut.money.transfer.app.account.MoneyTransferHandler;
import com.revolut.money.transfer.app.account.MoneyTransferHandlerFactory;
import com.revolut.money.transfer.domain.account.TransferResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountController {

    @GET
    @Path("/{accountId}/balance/")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalance(@PathParam("accountId") long accountId) {
        AccountBalanceHandler handler = AccountBalanceHandlerFactory.create();
        return Response.ok()
                .entity(handler.getBalance(accountId))
                .build();
    }

    @POST
    @Path("/transfer/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(TransferRequestDto dto) {
        MoneyTransferHandler handler = MoneyTransferHandlerFactory.create();

        TransferResult result = handler.transfer(dto.requestId, dto.senderId, dto.receiverId, dto.amount);

        return Response
                .ok()
                .entity(new TransferResponseDto(result.isSuccessful(), result.getDetails()))
                .build();
    }
}
