# parkmycar_webapp
This is a JEE Web application which serves as backend for ParkMyCar android application.

## Set up instructions
Install JDK 1.7 and make sure you have $JAVA_HOME set in environment variables (download it at http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) <br/>
Install MAVEN (download it at http://maven.apache.org/download.cgi) <br/>
Now open command line (type cmd in run prompt in windows), go to the folder of this project and type 'mvn clean install' If you see "BUILD SUCCESS" then you are good to proceed. <br/>
Download eclipse and install maven plugin (https://www.eclipse.org/m2e-wtp/) <br/>
Import the parkmycar_webapp project into eclipse via File->Import->Maven->Existing maven projects <br/>
Download and install tomcat <br/>
Install mysql database and mysql workbench.<br/>
Run the database seed script from <TBD> on your local databse.<br/>

<br/>
## Run instructions
Create tomcat server on eclipse. <br/>
Run the parkmycar_webapp on the server by Right click on the project -> 'Run As' -> 'Run on server' <br/>
You can now access the application at http://localhost:8080/parkmycar_webapp <br/>








