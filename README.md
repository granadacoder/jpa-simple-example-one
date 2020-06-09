
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
    
or the IntelliJ "workspace.xml" (usually at "\.idea\workspace.xml") syntax:

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
    
 or the IntelliJ "workspace.xml" (usually at "\.idea\workspace.xml") syntax:
 
       <envs>
         <env name="SPRING_DATASOURCE_URL" value="jdbc:sqlserver://localhost:1433;instanceName=SQLEXPRESSMyInstanceName;DatabaseName=MyDB;" />
         <env name="SPRING_DATASOURCE_USERNAME" value="mySqlAuthenticationUserName" />
         <env name="SPRING_DATASOURCE_PASSWORD" value="mySqlAuthenticationPassword" />
         <env name="SPRING_DATASOURCE_DRIVER-CLASS-NAME" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
       </envs>

Note, mySqlAuthenticationUserName probably needs sysadmin privileges
    
    
NOTE:

H2 vs MsSqlServer Manual Changes.
    
There is still one non-environment variable driven option in application.yml.

    properties:
      hibernate:
        dialect:    

For the above breadcrumb : you have to comment/uncomment out for H2 vs MsSqlServer


You'll need to comment/uncomment values in this file for the DateTimeOffset syntax:
    com.mycompany.organizationdemo.domain.constants.OrmConstants(.java)
    
and comments above it.  You'll need to change the column-type for the 2 different databases.
    
...............

POSTMAN Requests

    GET
    http://localhost:8080/api/v1/departments

    GET
    http://localhost:8080/api/v1/departments/333

    GET
    http://localhost:8080/api/v1/departments/beforecreatedate/2015-10-31T01:30:00.000-05:00

    GET
    http://localhost:8080/api/v1/departments/name/DepartmentOne

    GET
    http://localhost:8080/api/v1/departments/bykeys/111,333
    
    DELETE
    http://localhost:8080/api/v1/departments/222
    
    POST (as Json)
    http://localhost:8080/api/v1/departments/department
    
                {
                    "departmentName": "DepartmentNine",
                    "createOffsetDateTime": "2020-06-09T08:53:55.547-04:00",
                    "employees": [
                        {
                            "ssn": "000-00-9992",
                            "lastName": "Nottingham",
                            "firstName": "Nathanial",
                            "createOffsetDateTime": "2020-06-09T08:53:55.559-04:00",
                            "parentDepartmentKey": 999
                        },
                        {
                            "ssn": "000-00-9993",
                            "lastName": "Nottingham",
                            "firstName": "Nancy",
                            "createOffsetDateTime": "2020-06-09T08:53:55.559-04:00",
                            "parentDepartmentKey": 999
                        },
                        {
                            "ssn": "000-00-9991",
                            "lastName": "Nottingham",
                            "firstName": "Nina",
                            "createOffsetDateTime": "2020-06-09T08:53:55.559-04:00",
                            "parentDepartmentKey": 999
                        }
                    ]
                }        

and NotFound negative requests

    GET
    http://localhost:8080/v1/departments/-9999

    GET
    http://localhost:8080/v1/departments/name/DoesNotExist

