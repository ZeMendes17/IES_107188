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

Git basic commands:

$ cd project_folder # move to the root of the working folder to be imported
$ git init
# initialize a local git repo in this folder
$ git remote add origin <REMOTE_URL>
#must adapt the url for your repo
$ git add .
# mark all existing changes in this root to be commited
$ git commit -m "Initial project setup for exercise 1_3"
#create the
commit snapshot locally
$ git push -u origin main
#uploads the local commit to the shared repo

To see have a look at the repository history:
$ git log --reverse --oneline

NOTE: git commit messages are relevant and reflect the exercise you have completed, thus they should be clear and straight forward.


Docker:

On our project we need to create a file named Dockerfile inside the main directory of the project, example:
# syntax=docker/dockerfile:1

FROM node:18-alpine
WORKDIR /app
COPY . .
RUN yarn install --production
CMD ["node", "src/index.js"]
EXPOSE 3000

Next, we need to build the image:
$ docker build -t nameForTheFinalImage .
This command uses the Dockerfile  to build a new image
Using the example code above, we start from node:18-alpine image, if we do not have in our machine, Docker needs to download it.
Uses yarn to install the apps dependencies. The CMD directive specifies the default command to run when starting a container from this image. Finally, the -t flag tags your image.
The "." at the end of the docker build command tells Docker that it should look for the Dockerfile in the current directory.

To start an app container we use the command "docker run":
$ docker run -dp 127.0.0.1:3000:3000 imageName
-d (--detach), runs the container in the background
-p (--publish), creates a port mapping between the host and the container. Takes a String "HOST:CONTAINER", HOST -> address on the host; CONTAINER -> port of the container

To list the containers:
$ docker ps

When we update the source code, afterwards to run the app:
Build again -> $ docker build -t nameForTheFinalImage .
Remove the old container ->
$ docker ps (to get the container_id)
$ docker stop <the-container-id> (stop the container)
$ docker rm <the-container-id> (remove it) (-f after rm to force the remove)

Then we just have to start it again:
$ docker run -dp 127.0.0.1:3000:3000 imageName

SHARE THE APP

Go to https://hub.docker.com/ and create a repository
Then use the command "docker push"

Command to see my images: $ docker image ls
We need to tag our image to give it another name
Sign in to Docker Hub: $ docker login -u zemendes17
To tag the image: $ docker tag imageName zemendes17/imageName
Finally: docker push zemendes17/imageName

To test if the image is working just fine we need to run the app on a brand new instance that has never seen the container image. We can use: https://labs.play-with-docker.com/
And on the terminal: docker run -dp 0.0.0.0:3000:3000 zemendes17/imageName

PERSIST THE DB

Constainer's file system:
When a container runs, it uses the various layers from an image for its filesystem. Each container also gets its own "scratch space" to create/update/remove files. Any changes won't be seen in another container, even if they're using the same image. 

To access a container: $ docker exec <container-id> {$command} (ex: cat /data.txt)
Having a runnning container, to get the contents: docker run -it ubuntu ls /
Or we can use Docker Desktop to do the same. just open a terminal in the container and run the command.

PERSIST DATA
There are 2 methods but we will use volume mount for now
To create a volume: $ docker volume create name
We stop the container and run again using: $ docker run -dp 127.0.0.1:3000:3000 --mount type=volume,src=volumeName,target=/etc/dirName imageName

Or we can use Docker Desktop again to do the same. Create a volume and the when starting a container use the this in the options.

To inspect a volume: $ docker volume inspect {$volumeName}


