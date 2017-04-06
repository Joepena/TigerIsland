# TigerIsland
CEN3031 Term Project


<h1>HOW TO NETWORK IF UR INTO THAT</h1>
<b>Step 1</b>
<ul>Go to (src/main/java/) GameClient</ul>
<ul>Host can be changed to "localhost" for testing on your machine</ul>
<ul>If you want to test on my computer lmk and I'll turn it on</ul>
<ul>Port can be w/e u want, but stick with 8000 if u don't want to change ServerTest</ul>
<ul>If u do change the port number then in (src/test/java) ServerTest u need to update the port field</ul>
<ul>Open two terminals of ur choice</ul>
<ul><ul>On the first, compile then run ServerTest</ul></ul>
<ul><ul>On the second, compile then run GameClient</ul></ul>
<b>Note:</b> If u wild n u want to see different strings, change the outputLine variable's value

Added toString() to tuple class for debugging purposes
addPairTest() -> add overwridden equals() to Tuple because .assertEquals compares references

Fixed Left coordinate values?

existsAdjacentHexFailureTest, test was incorrect not function so we good

Attempt to fix settlement dfs functions and pass tests
-Settlement test infinite loop?
-Changed availablity grid to all true for debugging purposes
-Added is null check to location in dfs
-Used correctedOffset for coordinated that check availablilty grid
