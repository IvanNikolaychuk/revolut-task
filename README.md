# README #

# Design
1. Clean architecture - domain logic has no dependencies on infrastructure. Infrastructure layer can be changed without affecting domain model.
2. Having a `TransactionRequest` that registers a transaction before executing a transfer. This is done not to execute same logical transaction once (due to for example producer publishing same transfer request twice as technical duplicate)
3. Transaction from db perspective is handled by hibernate
4. Thread-safety is done using `jkeylockmanager` library.`ThreadSafeMoneyTransferHandlerDecorator` spec describes mechanism in more details. 

# API
GET: `/account/{accountId}/balance` - returns money amount for given account.  
POST: `/transaction/` - creates transaction. It is expected that each transfer would have it's own transaction  
POST: `/account/transfer` + `request body as json` - executes a transfer btw 2 accounts.
Example of request body:
`{"senderId":1 "receiverId":2 "amount":10,"requestId":"2692fc86-b354-4dc8-9eb6-bbb50e798918"}
`

#Potential improvements
1. Add archunit and unit tests that make sure `domain`, `controller` and `app` do not depend on `infrastructure` and hibernate.  

2. Replace static factories for creating objects with dependency-injection framework.

3. Replace json with avro that is more schema-evaluation friendly.

4. Remove explicit transactions and thread-safety and replace it with stateful stream-processing framework like `Apache Flink` that takes care about thread-safety, horisontal scalability, automated recovery and transactions.