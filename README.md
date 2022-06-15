# buildings-api-assignment-cnguyen

This is my submission of the take home assignment for the Software Engineer II role.

Included are my modifications to the skeleton code provided in the prompt.

### How to Run with Docker
Run using the provided Docker commands:
```
    docker build -t buildings-api-assignment .
    docker run -p8080:8080 buildings-api-assignment
```

**NOTE:** I was successful running the application as a Docker but I had to modify **FROM** command to include **--platform=linux/amd64** as I am running on a Mac with A1 chip.


### How to Run with Maven
Run using the provided Maven commands:
```
    mvn clean instal
    mvn spring-boot:run
```

## Route Endpoints

These are routes for this assignment. An URI example of how to get all sites can look something like this:

http://localhost:8080/sites/info?site_id=<site_id> where <site_id> is a number corresponding to the requested site.

<br />

| **Method**       | **Path**           | **Parameters**  | **Description**  |
|:-------------:|:-------------|:-----|:-------------|
| GET      | /sites/info | site_id | Returns a Site info given the **site_id**. The data is supplemented to include **total_size** and **primary_type**.
| GET      | /sites/all | |Return a list of all sites in the database|
| GET      | /sites/state | state | This will return a list of all Site within a state given the **state** parameter. i.e. **CA**
| POST      | /sites/create | name, address, city, state, zipcode| Create a new Site record with the passed parameters. This route will not however create new uses.
