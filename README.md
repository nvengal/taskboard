# Web Tasks Organizer
The purpose of this application is to give users the ability to keep track of the work that has been done, needs to be done, and who is working on everything. The current list of features include:
* Account Creation
* Creating Projects
* Adding tasks to projects
* Adding comments to a task
* Changing the status of a task via a drop down menu
* Dragging tasks through the different phases

Future updates and plans are working toward adding these features:
* Timer to track how long a task has been worked on/took to complete
* Inviting users to projects
* Add a deadline to each task
* Retrieve/change a forgotten password

## Getting Started
These are the steps you'll need to follow to get a local version of this application running on your machine for development, testing or demonstration puposes.

### Prerequisties
This is a Spring Boot Java application built using Maven. You will need internet access to connect to to the remote MySQL database. The IntelliJ IDEA IDE was used by our team for development.
* Java 1.8 (JDK) <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html> 
* Maven 3 <http://maven.apache.org/download.cgi>
* IntelliJ IDEA IDE <https://www.jetbrains.com/idea/download/>

After dowloading and installing Java and Maven set the appropriate environment variables.
```
The JAVA_HOME variable should point to your JDK installation
The PATH variable should include both the Java and Maven executable location ($location/bin) 
```
See the [maven install guide](https://maven.apache.org/install.html) for more specific instructions and troubleshooting.

Ensure that the prerequisites were correctly installed by running the following commands in the command prompt or terminal and ensuring that the executable is found and the version number is the expected value:
```
java -version
javac -version
mvn -version
```

### Build and Deployment
Maven is used to package the entire application into a single deployable jar.
1. Navigate to the root directory of the project source code in the command prompt or terminal.
2. Run ```mvn clean package```.
3. After the command has finished executing, in the target folder you should see ```s18webtasks-1.0-SNAPSHOT.jar```.
4. Since this is a Spring Boot application, it is built with an embedded webserver.
Deploying the application is a simple as running the following command from the target folder ```java -jar s18webtasks-1.0-SNAPSHOT.jar```.
5. After the webserver finishes starting up (less than 20 seconds), navigate to ```localhost:8080``` in a web browser to access the application.

### Local Development
Rebuilding and deploying the application each time a change is made is tedious.
The process can be simplified by utilizing an IDE such as IntelliJ.
1. Open the project in IntelliJ by selecting ```Import Project```.
2. Double click on the ```pom.xml``` file in the root directory of the project.
3. Click ```Next```.
4. You should see a single maven project to import ```org.franklin:s18webtasks:1.0-SNAPSHOT```. Make sure that it is selected and click ```Next```.
5. Select the location of your ```Java 8``` installation and click ```Next```.
7. Enter a name for the project and click ```Finish```.
8. In the IDE locate the ```src/main/java/taskboard/Application.java``` file.
9. Right click the file and select ```Run Application.main()```.
10. After the webserver finishes starting up (less than 20 seconds), navigate to ```localhost:8080``` in a web browser to access the application.
11. The webserver can be restarted each time a code change is made without having to rebuild the project.

## Contributors
* James Groves -- Team Lead
* Nikhil Vengal
* Trent Knoche

