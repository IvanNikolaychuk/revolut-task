package com.revolut.money.transfer.domain.account;

/**
 * Responsible for storing results related to transfer execution.
 *
 * Event though there is no TransferExecution object on db level, storing results of execution (change in accounts, transaction  * request) should be done in a transactional way (which involves interaction with underlying transaction mechanism). Since we
 * don't want application and domain layer being aware of low-level details related to transaction management, we put this
 * functionality on repository level and have a domain object aggregate that describes transfer execution.
 *
 */
public interface TransferExecutionRepository {
    void save(TransferExecution transferExecution);
}
