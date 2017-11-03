## USCS-MDM
### Start guide
#### How to install

Project uses Maven, so everything you need to do is to download the project and run `mvn`commands from under project root dir.
#### How to run tests
File `testng.xml` under `src/test/resources` contains list of tests to run. You can change it in order to remove some tests, change the order etc.
`mvn test` runs whole test suite. You can also run particular tests using command `mvn test -Dtest=TestName1,TestName2` etc.
DON'T forget to specify profiles. You should use at least one profile which define URL to run tests on (9109,9111,9113,local9081). So, in order to run CreateAndViewCreatedCustomerTest on UAT:9113 we should use `mvn test -Dtest=CreateAndViewCreatedCustomerTest -P 9113`
#### Profiles
Test's input data lives in properties
We pass input data in tests(url,username,password etc.) using maven profiles.
Profile contains some information that is specific for environment, user etc.
All profiles are listed in maven configuration file (`pom.xml` in project root). They can be active or not. Depends on active profiles tests get different properties.
There are different properties for different os (linux, windows, mac) and for different environments (local, 9109,9111,9113).
You can activate profiles by passing argument in command line. For example `mvn verify -P mac,9113`. There is not point in activating few profiles from the same group (9109 and 9111) since the properties from the last one will override previous.
Some profiles are active by default (9113 is default active profile), moreover some profiles will be activated automaticlly depends on your OS.
If you want, you can manually change properties in profiles, but itsn't recommended.
##### Intelij IDEA
If you use IDEA you can activate profiles under `Maven Project` tab (right side of the window).
To run specific test just open it's class and click on green play button at the left side of class editor (NO reports will be generated in this case).
To run whole test suite defined in `testng.xml` you should create new testng run configuration (`Run configurations` window -> new testng configuration and select `Suite` value for `test kind`input and specify the location of `testng.xml` below (reports WILL be generated).
#### Reports
After all tests finish test report will be generated. It lives under `target/surefire-reports` dir. Just open `index.html` document. You'll also be able to see screenshots for all failed tests.
