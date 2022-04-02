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

# Run Test

- mvn test

# Show test coverage in intellij
- Running test will create target/jacoco.exec
- Run -> Show Coverage Data --> select the jacoco.exec file

# TradeOffs
- Assuming max room size coordinate X and Y in range (0 - Integer.MAX_VALUE)
- No Validation of patches coordinate range, Won't pick the patch if it is not in Rooms Size max coordinate range.
- Creating the Navigation Request in DB before starting the actual navigation.
   So if something went wrong there will be an entry for the request without a related result entry in DB

# Future Scope
- Can bring a status to the navigation request entry in DB and update it to completed once the navigation result is obtained.
- Set up a cron to check the pending navigation request and re-trigger it.
- Can check for duplicate requests and obtain the result directly from DB rather than executing the whole navigation.
    



