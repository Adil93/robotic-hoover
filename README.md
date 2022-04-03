# Robotic Hoover Application
An imaginary Java Robotic Hoover Application
- Maven SpringBoot framework

# Steps to Run

From the project root folder, Execute below steps:

- mvn clean install
- Run using the jar file created:
  - java -jar target/robotic-hoover-1.0-SNAPSHOT.jar
- Run using the maven command:
  - mvn springboot:run

# API

- Robotic Hoover Navigation API
  - POST {host}/robotic/hoover
  - Request : 
```json
  {
    "roomSize": [ 5, 5 ],
    "coords": [ 1, 2 ],
    "patches": [
        [ 1, 0 ],
        [ 2, 2 ],
        [ 2, 3 ]
    ],
    "instructions": "NNESEESWNWW"
}
```


- Response:
```json
{
    "coords": [ 1, 3 ],
    "patches": 1
}
```
- Error Responses :
  - Sample Missing Attribute Response 
```json
{
    "errorId": "1474d8f1-35ec-40c7-9695-793000b5561c",
    "errorCode": "INVALID_REQUEST",
    "status": 400,
    "message": "Invalid request body or parameters supplied",
    "errors": [
        {
            "name": "coords",
            "details": "must not be empty"
        }
    ]
}
```
- Sample UnProcessableEntity Exception
```json
{
    "errorId": "4869130f-2e1b-46ec-9587-ebfa82109c7a",
    "errorCode": "INVALID_COORDINATE_VALUE",
    "status": 422,
    "message": "Invalid Coordinate values",
    "errors": []
}
```

# Run Test

- mvn test

# Show test coverage in intellij
- Running test will create target/jacoco.exec
- Run -> Show Coverage Data --> select the jacoco.exec file

# Accessing H2 DB
- {host}/robotic/h2-console
  - eg: localhost/robotic/h2-console  - (credentials in properties file)

# TradeOffs
- Assuming max room size coordinate X and Y in range (0 - Integer.MAX_VALUE)
- No Validation of patch coordinate range, Algorithm won't pick the patch if it is not within the Room's maximum coordinate range.
- Create the Navigation Request in DB before starting the actual navigation. If something went wrong there will be an entry for the request without a related result entry in DB
- Used H2 DB for the demo purpose. You can bring up a specific database using docker and give the data source info in properties file.

# Future Scope
- Can bring a status field to the navigation request entity and update it to complete once the navigation result is obtained.
- Set up a cron to check the pending navigation request and re-trigger it at EOD.
- Can check for duplicate requests and obtain the result directly from DB rather than executing the whole navigation.
- Can change the switch case in canMove function and introduce a Direction interface with a move API.
  Each direction can have classes implementing the Direction interface.


