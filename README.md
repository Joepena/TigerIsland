# TigerIsland
CEN3031 Term Project - Team K

## Features
There are no features missing as far as game functionality goes. Our AI strategy prioritizes as follows:
  1. Tigers
  2. Meeples
  3. Totoros
  
Our program implements a "rush" strategy by focusing on placing all Meeples and Tigers, resulting in a 200 point tournament win. While there are tiger pieces remaining, the program will nuke tiles and found settlements closest to the origin. After all tiger pieces have been placed, the program will nuke tiles closest the origin, and expand settlements with a focus on placing the most Meeples as possible. 

## Instructions
1. If you're running from within IntelliJ:
  ```
  Run > Edit Configurations > Application > GameClient
  ```
2. If you wish to test in intellij you right click src/test/java folder and press 'Select Tests in Java'

3. If you wish to run tests from the terminal run: `./gradlew clean test` make sure you are in the project directory before using this command.
  
**Program Arguments:** `<host_name> <port> <username> <password> <tournament_password>`

