package com.nttdata.bootcamp.debitcardstatementservice.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountContract {

    /** hasMaintenanceFee? */
    private Boolean hasMonthlyMaintenanceFee = Boolean.FALSE;
    /** maintenanceFee. */
    private BigDecimal monthlyMaintenanceFee;

    /** hasDailyOperationLimit? */
    private Boolean hasDailyTransactionLimit = Boolean.FALSE;
    /** dailyOperationLimit. */
    private Integer dailyTransactionLimit;

    private Boolean hasMinimumInitialBalance = Boolean.FALSE;
    private BigDecimal minimumInitialBalance;

    /** hasMonthlyOperationLimit? */
    private Boolean hasMonthlyTransactionLimit = Boolean.FALSE;
    /** monthlyOperationLimit. */
    private Integer monthlyTransactionLimit;

    /** Fee for transactions after limit. */
    private BigDecimal transactionFeeAfterLimit;

    /** Additional DailyAverageBalance */
    private Boolean requiresDailyAverageBalance = Boolean.FALSE;
    private BigDecimal dailyAverageBalanceLimit;
}
