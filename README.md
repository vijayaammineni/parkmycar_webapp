# parkmycar_webapp
This is a JEE Web application which serves as backend for ParkMyCar android application.

## Set up instructions
Install JDK 1.7 and make sure you have $JAVA_HOME set in environment variables (download it at http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) <br/>
Install MAVEN (download it at http://maven.apache.org/download.cgi) <br/>
Place settings.xml file from this project in C:/Users/<your_user_name>/.m2/ folder in your machine <br/>
Now open command line (type cmd in run prompt in windows), go to the folder of this project and type 'mvn clean install' If you see "BUILD SUCCESS" then you are good to proceed. <br/>
<br/>

Download eclipse and install maven plugin (https://www.eclipse.org/m2e-wtp/) <br/>
Import the parkmycar_webapp project by select Import->Maven->Existing maven projects <br/>
Download and install tomcat <br/>
Create tomcat server on eclipse and now you can run the parkmycar_webapp on that server. <br/>







