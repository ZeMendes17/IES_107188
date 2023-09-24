Maven is a build management tool

To create a new maven project:
--> mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

In particular, “groupId” and “artifactId” should be specific for each project.

This generates the directory "src" and a pom.xml file that can be used to add dependencies later on.

In order to compile and run the project:

--> mvn package #get dependencies, compiles the project and creates the jar
--> mvn exec:java -Dexec.mainClass="{DgroupId.MainFuncName}" #adapt to match your own package structure and class name
Obs: If we want to add parameters use: -Dexec.args="{one or more args}"

To write a log we can use Log4j2 in java.
Add the dependencies to pom.xml:
	<dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.6.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.6.1</version>
        </dependency>
And create a file named log4j2.xml in the Maven resources folder.
We can add log through file or console or even both. This is done by adding appenders in the
xml file (File and Console) and then referencing them in loggers as root.
