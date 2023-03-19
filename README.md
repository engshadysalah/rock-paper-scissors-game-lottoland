## About The Project

 • This is an implementaion for Rock Paper Scissors Game, (https://en.wikipedia.org/wiki/Rock-paper-scissors).

• There will be 2 kinds of players, one should always choose randomly, the other should always choose rock.

• For more detials about the impleminted senarios, (https://drive.google.com/drive/folders/1ARWWe9ILA-jH_bOcHl0CetM73Q2OUpWg?usp=share_link)


## Built With

• This Project built with SpringBoot, Junit and Maven for the backend [lottoland-game-backend] and Angular for the Frontend [lottoland-game-client]


## Installation

   ```sh
[1]: Clone the repo
   git clone https://github.com/engshadysalah/rock-paper-scissors-game-lottoland.git

   ```
   ```
[2]: For the backend [lottoland-game-backend], Just you need to build the project with Maven.
   ```

   ```sh
[3]. For the Frontend, [lottoland-game-client], You need to install packages with npm, then run the project using angular command.

   npm install
   ng serve
   then access this url for the browser [http://localhost:4200/]
   ```

## API Requests
The backend [lottoland-game-backend] provides 3 API requests, Please find the post man collection taht can be used test the Rest API [lottoland-game-client-postman-api-requests.postman_collection] in the parent directory of the project [/rock-paper-scissors-game-lottoland]

[1]: play reuest, That used to play a new round per session, it returns all the roundes  and the rounds number as well for the currunt session, 

   ```sh
curl --location 'http://localhost:8080/api/v1/game/play' \
--header 'Cookie: JSESSIONID=78336BF5642FB4A77757917EE0A08712' \
--data ''
   ```

  
[2]: restart request, That used to restart the game per session, so it will clear all the current rounds.
   ```sh
curl --location --request POST 'http://localhost:8080/api/v1/game/restart' \
--header 'Cookie: JSESSIONID=78336BF5642FB4A77757917EE0A08712'
   ```


[3]. get/allRoundsResultForAllSessions reuest, 
* That used to returns  an HashMap object that hold
    * Total rounds played
    * Total Wins for first players
    * Total Wins for second players
    * Total draws
    * These totals should consider all the rounds of all the games played by all users.
    *   (even if we clicked in "Restart button", these games should be considered as well)

   ``` sh
      curl --location 'http://localhost:8080/api/v1/game/get/allRoundsResultForAllSessions' \
   --header 'Cookie: JSESSIONID=78336BF5642FB4A77757917EE0A08712' \
   --data ''


## How can the Game be tasted
The frontend not finish yet, so You can test the game by having 3 different users session:
* Normal Browser Window, 
   * Access the url for first request: [http://localhost:8080/api/v1/game/play] to play game and return round results.

   * Access The Url for the third request [http://localhost:8080/api/v1/game/get/allRoundsResultForAllSessions] to get Total rounds played, Total Wins for first players, Total Wins for second players, and Total draws the rounds for all of the user sessions.


* Incognito windo in the Browser, and access the url for first request: [http://localhost:8080/api/v1/game/play]
   * Access the url for first request: [http://localhost:8080/api/v1/game/play] to play game and return round results.

   * Access The Url for the third request [http://localhost:8080/api/v1/game/get/allRoundsResultForAllSessions] to get Total rounds played, Total Wins for first players, Total Wins for second players, and Total draws the rounds for all of the user sessions.

* Postman Collections [lottoland-game-client-postman-api-requests.postman_collection] in the parent directory of the project [/rock-paper-scissors-game-lottoland]:
   * Send the first request [playAndGetAllRoundsDetailsPerSingleSession], to play game and return round results.
   * Send the second request [restartGame], to restart the game.
   * Send the third request [allRoundsTotlalResultsForAllSessions], to get Total rounds played, Total Wins for first players, Total Wins for second players, and Total draws the rounds for all of the user sessions.

## Git Branches
   * Please use the latest changes of the main branch to run the applications, because the main branch is updated and has all of the latest changes for the backedn and frontend.

   * There're a dev branch for the backend app [dev-backend] and also for the fronted app [dev-frontend], both of them not have the latest changes, also both of them are merged to the main branch then I continue working on the main branch to save time for switching bettwen the branches and the applications and also to work on the 2 appications [backend and frontend] at the same time.

