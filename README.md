# An example jersey application for zipkin tracing using Brave4

Before building this program, please modify "ZIPKIN-SERVER" and "QUERY-PORT" to appropriate values in generateTrace.java.

1) compile

mvn compile

2) build package

mvn package

3) Start program

./Exec.sh

After starting the program, you can a obtain reposonse from http://localhost:9000/ like below.

{"Time":"Fri Oct 13 00:41:18 EDT 2017"}

This response is originally generated in "serviceBack" class which serves at 9001 port of localhost.
serviceFront" class call back to 9001 port of localhost and servers at 9000 port.

[Request]
User(wget, curl) ---9000--> serviceFront(localhost) ---9001--> serviceBack(localhost)

[Response]
User(wget, curl) <--9000--- serviceFront(localhost) <--9001--- serviceBack(localhost)

These two services can send Span to zipkin server at every request/response using generateTave class.

If you hoped to implement this program to other servers and other ports, please modify string variable fronthost and string variable backhost, "9000", "9001" in serviceFront.java and serviceBack.java.

Good Luck!

Bunichiroh Fujii <bfujii@ba2.so-net.ne.jp>
