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
- No Validation of patch coordinate range, Algorithm won't pick the patch if it is not in the Room's maximum coordinate range.
- Create the Navigation Request in DB before starting the actual navigation. So if something went wrong there will be an entry for the request without a related result entry in DB

# Future Scope
- Can bring a status field to the navigation request entity and update it to complete once the navigation result is obtained.
- Set up a cron to check the pending navigation request and re-trigger it at EOD.
- Can check for duplicate requests and obtain the result directly from DB rather than executing the whole navigation.


