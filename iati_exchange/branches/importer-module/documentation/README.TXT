RUN THE JETTY SERVER FROM COMMAND LINE:
mvn jetty:run
OR
mvn jetty:run & (to run in the background)
OR
nohup mvn jetty:run & (to run even after you log out - from ssh for example )

STOP THE JETTY SERVER
CTRL-C ( in case you started with  mvn jetty:run )
OR
mvn jetty:stop

CREATE WAR FILE (for tomcat for example)
mvn package ( create iati.war in /target folder )
