# optic-technical-assessment

To complete this assessment, please fork this repo and implement your solution. Feel free to update the readme with specific instructions
 (address, port, etc) for accessing your application.

Goal: Create a REST service to do CRUD operations on the database table that docker-compose sets up

The operations we'd like to see supported are 
 - List, lookup, and add new attacks
 - List, lookup and add new bots
 - Find attacks in a particular date range
 - When displaying an attack, list the bot names that participated
 
## Rest API
- All endpoints are under the context path /api/v1
- All data is in JSON format
- Any request that is malformed will receive a 400 response
- A lookup request that is properly formed but does not match a known identifier will receive a 404 response
- Add requests will receive the added entity in the response

### Bot Endpoints
- GET `/bots/id/{id}` - lookup a bot by ID
- GET `/bots/all` - list all bots
- POST `/bots/add/{name}` - add a bot with the specified name

### Attack Endpoints
- GET `/attacks/id/{id}` - lookup an attack by ID
- GET `/attacks/all` - list all attacks
- GET `/attacks/find` - requires `start` and `end` querystring parameters in ISO date time format, e.g. `2022-01-01T00:00:00.000-05:00`. *Be sure* to URL-encode the parameter values, otherwise +GMT offsets will be interpreted incorrectly!
- POST `/attacks/add` - requires a JSON request body in the form `{"site":"example.com", "attackDate": "2022-01-01T12:00:00.000-05:00", "botIds":[1, 2, 3]}`. Bot IDs are IDs of **previously added** bots. If at least one specified bot ID is valid, the attack will be added with the valid bot IDs associated to it.

## Testing
I've added a single unit test for the AttackService class to demonstrate my approach for separating concerns and mocking dependencies. In a real application there would also be integration tests to ensure that the controllers work as expected. I don't feel that unit tests for the controllers are particularly helpful, as their correctness relies on Spring behavior such as @Validated, which a unit test would not capture.
 
## Requirements
 - Use the provided MySQL database
    - `jdbc:mysql://mysqldb:3306/optic_data?useSSL=false`
    - Username and password are both `spring`
 - Use Spring boot to build your app/service. You can use whatever libraries and build system you like.
 - Add your Spring service to docker-compose.yaml so that it starts on `docker compose up --build` and is available on port 8080
 
## Schema

There are three tables we've already created for you to use:
 - bots: Defines a bot
    - `id` INT AUTO INCREMENT
    - `name` VARCHAR
 - attacks: Defines an attack
     - `id` INT AUTO INCREMENT
     - `site` VARCHAR
     - `attack_date` DATE
 - attack_bots: Defines the bots that participated in an attack
    - `bot_id` INT
    - `attack_id` INT
