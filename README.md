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

   ```
[3]. For the Frontend, [lottoland-game-client], You need to install packages with npm, then run the project using angular command.

   npm install
   ng serve
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
    * ◦ Total rounds played
    * ◦ Total Wins for first players
    * ◦ Total Wins for second players
    * ◦ Total draws
    * • These totals should consider all the rounds of all the games played by all users.
    *   (even if we clicked in "Restart button", these games should be considered as well)

   ``` sh
      curl --location 'http://localhost:8080/api/v1/game/get/allRoundsResultForAllSessions' \
   --header 'Cookie: JSESSIONID=78336BF5642FB4A77757917EE0A08712' \
   --data ''
