# cvs-auto-svc-vott
This is the project for creating and managing the VOTT services automated tests
## Getting Started
These instructions will get you up and running with the automation framework.
### Prerequisites
- Browserstack credentials
- Jenkins access
- Java 1.8 SDK
- Maven
- Git
- IntelliJ
###Running Locally
In order to run the test locally your will need to complete the following steps:
- Connect to Jenkins VPN (required for database access)
- Populate the template config file: src>main>resources>config.json
  !!Ensure only the template is committed to Git!!
  Running can be triggered from IntelliJ
## Running the Tests
Pull the repo
Run each test individually in each of the following folders
Database integrity tests (src/test/java/vott/database)
Document retrieval service (src/test/java/vott/documentretrieval)
Enquiry service (src/test/java/vott/testhistory)
E2E tests (src/test/java/vott/e2e/E2eTest.java)
## Planned improvements
Extend local reporting
Adding test @Titles for database tests (link with Jira ACs)
## Contributors
-Robert Whitehouse @RobWhitehouseBJSS
-Emanuel State @Estatebjss
-Paul Benn @PaulBenn-BJSS