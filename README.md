
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
    
or the IntelliJ "workspace.xml" syntax:

      <envs>
        <env name="SPRING_DATASOURCE_URL" value="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;LOCK_MODE=0" />
        <env name="SPRING_DATASOURCE_USERNAME" value="myUserName" />
        <env name="SPRING_DATASOURCE_PASSWORD" value="" />
        <env name="SPRING_DATASOURCE_DRIVER-CLASS-NAME" value="org.h2.Driver" />
      </envs>

Ms Sql Server

    SPRING_DATASOURCE_URL
    jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESSMyInstanceName;DatabaseName=MyDB;

    SPRING_DATASOURCE_USERNAME
    mySqlAuthenticationUserName
    
    SPRING_DATASOURCE_PASSWORD
    mySqlAuthenticationPassword
    
    SPRING_DATASOURCE_DRIVER-CLASS-NAME
    com.microsoft.sqlserver.jdbc.SQLServerDriver

Note, mySqlAuthenticationUserName probably needs sysadmin privileges
    
    
NOTE:

H2 vs MsSqlServer Manual Changes.
    
There is still one non-environment variable driven option in application.ym.

    properties:
      hibernate:
        dialect:    

You have to comment/uncomment out for H2 vs MsSqlServer

There is also a JPA Annotation discrepancy in Department.java.  Find "private OffsetDateTime createOffsetDateTime;" and comments above it.
    
...............

POSTMAN Requests

    GET
    http://localhost:8080/v1/departments

    GET
    http://localhost:8080/v1/departments/333

    GET
    http://localhost:8080/v1/departments/beforecreatedate/2015-10-31T01:30:00.000-05:00

    GET
    http://localhost:8080/v1/departments/name/DepartmentOne


and NotFound negative requests

    GET
    http://localhost:8080/v1/departments/-9999

    GET
    http://localhost:8080/v1/departments/name/DoesNotExist

