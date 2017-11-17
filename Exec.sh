#!/bin/bash

if [ ! -d ./target/dependency ]; then
	/usr/bin/mvn dependency:copy-dependencies
fi

${JAVA_HOME}/bin/java -Djava.net.preferIPv4Stack=true -cp 'target/dependency/*:target/brave-jersey-example-0.0.1-SNAPSHOT.jar' brave.jersey.serviceFront &
${JAVA_HOME}/bin/java -Djava.net.preferIPv4Stack=true -cp 'target/dependency/*:target/brave-jersey-example-0.0.1-SNAPSHOT.jar' brave.jersey.serviceBack &
