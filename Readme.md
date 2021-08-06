### AddressBook ###

### User Story ###

As a Reece branch manager <br>
I would like an address book application<br>
so that I can keep track of my customer contacts.

#### Acceptance Criteria ####

  - Address book will hold name and phone numbers of contact entries
  - Create REST API with the endpoint of following.
    - Users should be able to add new contact entries
    - Users should be able to remove existing contact entries
    - Users should be able to print all contacts in an address book
    - Users should be able to maintain multiple address books
    - Users should be able to print a unique set of all contacts across multiple address books
  
## Solution Description ##

### API-Spec ###
```
  addressbook-api-spec.yaml
``` 
### Solution - How to run ###

* Dependencies
  - java 8
  - Gradle

* Build and run unit tests

```bash
    cd ${PROJECT_HOME}
    ./gradlew clean build
    # build task execute all the test.
```

* Run executables in docker
```bash
    cd ${PROJECT_HOME}
    docker build -t addressbook:latest .
    docker run -p 8080:8080 addressbook:latest .
    
```




