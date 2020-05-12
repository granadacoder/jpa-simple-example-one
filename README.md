
    Startup Class:
    com.mycompany.organizationdemo.businessservices.MyRestServiceStartupApplication

Environment Variables (need to set them under Run/Configurations)

For (H2 In Memory)

    SPRING_DATASOURCE_URL
    jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;LOCK_MODE=0

    SPRING_DATASOURCE_USERNAME
    myUserName

    SPRING_DATASOURCE_PASSWORD
    (empty string , aka no value for H2)

    SPRING_DATASOURCE_DRIVER-CLASS-NAME
    org.h2.Driver

Ms Sql Server

    SPRING_DATASOURCE_URL
    jdbc:sqlserver://localhost\\SQLEXPRESSAkaMyInstanceName:1433;DatabaseName=MyDatabaseDB;"/>-->

    SPRING_DATASOURCE_USERNAME
    mySqlAuthenticationUserName
    
    SPRING_DATASOURCE_PASSWORD
    mySqlAuthenticationPassword
    
    SPRING_DATASOURCE_DRIVER-CLASS-NAME
    com.microsoft.sqlserver.jdbc.SQLServerDriver
    
...............

POSTMAN Queries

GET
http://localhost:8080/v1/departments

GET
http://localhost:8080/v1/departments/333

GET
http://localhost:8080/v1/departments/beforecreatedate/2015-10-31T01:30:00.000-05:00