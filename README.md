# Chat_client
I am using an executable jar to run this client.
The server to connect to is running on ip - 52.36.79.34 (AWS) and port - 49129

Use the server address and port-number as the two arguments to run this client.
You can have multiple instances of this client to chat with one another.
Once a client who has joined any chatroom enters a message, the message gets circulated to all members of the chatroom.
Run this as:
> java Client 52.36.79.34 49129

Note: this has been built using Java 7.

Below is the transcript of how to use this client to connect to my AWS server: 
C:\Users\Cyril Shelke\Documents>java -jar client.jar localhost 1220
Establishing connection. Please wait ...
Connected to Cyril's server:
<= Welcome to Cyril's chat server
<= Login Name?
Ron
<= Welcome Ron!
/rooms
<= *business(0)
<= *chat(1)
<= *hottub(0)
<= end of list
/join chat
<= entering room: chat
<= *xyz
<= *Ron (you)
<= end of list
<= xyz: hi Ron
/rooms
<= *business(0)
<= *chat(2)
<= *hottub(0)
<= end of list
/leave
<= * user has left room: Ron
/quit
<= BYE!
Disconnected from the chat server
