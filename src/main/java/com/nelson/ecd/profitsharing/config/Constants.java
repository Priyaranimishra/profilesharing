package com.nelson.ecd.profitsharing.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_SWAGGER = "swagger";
    // Spring profile used to disable running liquibase
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_SUCCESS = "Success";
    
    public static final String EXACT_AGE_INDICATOR = "exactAge";
    public static final String PLAN_PERIOD_AGE_INDICATOR = "planPeriod";
    
    public static final String SERVICE_START_DATE_DATE_OF_HIRE_INDICATOR = "dateOfHire";
    public static final String SERVICE_START_DATE_PLAN_ENTRY_DATE_INDICATOR = "planEntryDate";
    
    public static final String PROFIT_SHARING_METHOD_FLAT_AMT = "flatAmt";
    public static final String PROFIT_SHARING_METHOD_PRORATED_BY_COMPENSATION = "proratedByCompensation";
    public static final String PROFIT_SHARING_METHOD_PRORATED_BY_ACCT_BALANCE = "proratedByAcctBalance";
    public static final String PROFIT_SHARING_METHOD_BASED_ON_PCT_COMPENSATION = "pctCompensation";
    public static final String PROFIT_SHARING_METHOD_BASED_ON_AGE_SERVICE_COMPENSATION = "ageServiceCompensation";
    
    private Constants() {
    }
}
