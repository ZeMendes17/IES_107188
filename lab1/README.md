# Maven
Maven is a build management tool.

In order to create a new Maven Project:

```
$ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
> In particular, "groupId" and "artifactId" should be specific for each project.

In order to compile and run the project:
```
$ mvn package
$ mvn exec:java -Dexec.mainClass="{groupId.MainFuncionName}"
```
> The first command gets the dependencies, compiles the project and creates the jar file.

> The second one will execute. It needs to be adapted to match our own package structure and class name.

> Obs: If we want to add arguments to the main function we can use: `-Dexec.args="{one or more args}"`.

### Write a log
In Java we can easily write a log using a logging library such as **Log4j2**.
Using Maven we just need to add the library dependecies to *pom.xml*:
```
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
```

And create a file named *log4j2.xml* in the Maven **resources** folder.
We can add log through file or console or even both. This is done by adding appenders in the
xml file (File and Console) and then referencing them in loggers as root.

## Maven Command Sheet
### Command to get the dependencies, compile the project and create the jat file:
```
$ mvn package
```
### Command where the project is cleaned to remove all previous-build files generated:
```
$ mvn clean
```
> Can be combined with other commands, ex: `$ mvn clean package`

### Command to deploy the packaged jar/war files, storing them as classes in the local repository:
```
$ mvn install
```
> If we want to use a Maven project as a dependency to another, we need to use this command so it can be a dependency in the *pom.xml*

### Command to execute the compiled Project:
```
$ mvn exec:java -Dexec.mainClass="{groupId.mainClass}"
```
> Used after `$ mvn package`.If we want to pass arguments we use `-Dexec.args="{one or more args}"`

# Git
### Basic Commands:
```
$ cd project_folder # move to the root of the working folder to be imported
$ git init
# initialize a local git repo in this folder
$ git remote add origin <REMOTE_URL>
#must adapt the url for your repo
$ git add .
# mark all existing changes in this root to be commited
$ git commit -m "Initial project setup for exercise 1_3"
#create the commit snapshot locally
$ git push -u origin main
#uploads the local commit to the shared repo
```

### To have a look at the repository history:
```
$ git log --reverse --oneline
```
> Note: git commit messages are relevant and reflect the exercise you have completed, thus they should be clear and straight forward.

# Docker
On our project we need to create a file named **Dockerfile** inside the main directory of the project, example:
```
# syntax=docker/dockerfile:1

FROM node:18-alpine
WORKDIR /app
COPY . .
RUN yarn install --production
CMD ["node", "src/index.js"]
EXPOSE 3000
```
Next we need to build the image:
```
$ docker build -t nameForTheFinalImage .
```
> This command uses the *Dockerfile* to build a new image

Using the example above, we start from **node:18-alpine image**, if we do not have in our machine, Docker needs to download it.
Creates a directory app and copies the files from our directory and pastes them to the Container.
Uses yarn to install the apps dependencies. The CMD directive specifies the default command to run when starting a container from this image. Finally, the -t flag tags your image.
The "." at the end of the docker build command tells Docker that it should look for the Dockerfile in the current directory.


To start an app container we use the command **docker run**:
```
$ docker run -dp 127.0.0.1:3000:3000 imageName
```
> -d (--detach), runs the container in the background

>-p (--publish), creates a port mapping between the host and the container. Takes a String "HOST:CONTAINER", HOST -> address on the host; CONTAINER -> port of the container

To list the containers:
```
$ docker ps
```

When we update the source code, afterwards to run the app:
```
$ docker build -t nameForTheFinalImage .
$ docker ps
$ docker stop <the-container-id>
$ docker rm <the-container-id>
```
> 1. Build again

> 2. Get the container id

> 3. Stop the container

> 4. Remove it. If we use *-f* after *rm* we do not need to do step 3, it forces the remove even if it is still running

Finnally start it again:
```
$ docker run -dp 127.0.0.1:3000:3000 imageName
```

## Share the App

Go to https://hub.docker.com/ and create a repository
Then use the command:
```
$ docker push
```

Command to see my images: 
```
$ docker image ls
```
We need to tag our image to give it another name
Sign in to Docker Hub: 
```
$ docker login -u zemendes17
```
To tag the image: 
```
$ docker tag imageName zemendes17/imageName
```
Finally:
```
$ docker push zemendes17/imageName
```

To test if the image is working just fine we need to run the app on a brand new instance that has never seen the container image. We can use: https://labs.play-with-docker.com/
And on the terminal: docker run -dp 0.0.0.0:3000:3000 zemendes17/imageName

## Persist the DB
Constainer's file system:
When a container runs, it uses the various layers from an image for its filesystem. Each container also gets its own "scratch space" to create/update/remove files. Any changes won't be seen in another container, even if they're using the same image. 

To access a container: 
```
$ docker exec <container-id> {$command} (ex: cat /data.txt)
```

Having a runnning container, to get the contents: 
```
$ docker run -it ubuntu ls /
```
Or we can use Docker Desktop to do the same. just open a terminal in the container and run the command.

### Persist data
There are 2 methods but we will use volume mount for now
To create a volume: 
```
$ docker volume create name
```
We stop the container and run again using: 
```
$ docker run -dp 127.0.0.1:3000:3000 --mount type=volume,src=volumeName,target=/etc/dirName imageName
```
Or we can use Docker Desktop again to do the same. Create a volume and the when starting a container use the this in the options.

To inspect a volume: 
```
$ docker volume inspect {$volumeName}
```

## Docker Compose
After having our Dockerfile we can create a compose.yaml file. 

A docker compose file is used to run multi-container Docker applications.
Example:
```
services:
  web:
    build: .
    ports:
      - "8000:5000"
  redis:
    image: "redis:alpine"
```

> This file has 2 services: web and redis
web, uses image from Dockerfile, and
redis, uses a public Redis image pulled from Docker Hub.

### Docker Compose Commands:
Command to start the app:
```
$ docker compose up
```
> Using docker image ls we can see both images. Inspect them by using `$ docker inspect <tag or id>`

Command to stop the app:
```
$ docker compose down
```

Another example:
```
services:
  web:
    build: .
    ports:
      - "8000:5000"
    volumes:
      - .:/code
    environment:
      FLASK_DEBUG: "true"
  redis:
    image: "redis:alpine"
```
> Volumes mounts the project directory on the host to /code inside the container, allowing us to modify the code on the fly without rebuild.

> Environment sets the FLASK_DEBUG environment variable (tells flask to run in development mode and reload the code on change).

Command to run the services in the background:
```
$ docker compose up -d
```

Command to run one off the commands from the server:
```
$ docker compose run {command}
```
> Example for {command}: web env

Command to stop the services once they are finnished:
```
$ docker compose stop
```

Command to bring everything down, removing the containers entirely:
```
$ docker compose down --volumes
```
>  --volumes removes the data volume used (Redis container).

### Using Docker on a Maven Project
In order to use Docker on a Maven Project we need to build a **Dockerfile** in the main directory (next to *src* and *pom.xml*).
Then we need to add add a **pugin** to the **pugins** inside **build** in the *pom.xml*:
```
<plugin>
    <!-- Build an executable JAR -->
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <archive>
            <manifest>
                <addClasspath>true</addClasspath>
                <mainClass>{groupId.mainClass}</mainClass>
            </manifest>
        </archive>
    </configuration>
</plugin>
```
Afterwards we run the command:
```
$ mvn clean package
```
And use the generated jar in the *Dockerfile*.
This jar contains everything needed to run the app.

## Docker Command Sheet

### Command to build the image:
```
$ docker build -t nameForTheFinalImage .
```

### Command to start the app container
```
$ docker run -dp 127.0.0.1:3000:3000 imageName
```
> -d (--detach), runs the container in the background

> -p (--publish), creates a port mapping between the host and the container. Takes a String "HOST:CONTAINER", HOST -> address on the host; CONTAINER -> port of the container

### Command to list all the images:
```
$ docker image ls
```

### Command to list all the containers:
```
$ docker container ls
```

### Command to list the active containers:
```
$ docker ps
```

### Command to stop a container:
```
$ docker stop <the-container-id>
```

### Command to remove a container:
```
$ docker rm <the-container-id>
```
> We can use *-f* after rm to force the remove, not needing to stop the container.

### Command to access a container:
```
$ docker exec <container-id> {$command}
```

### Command to get the contents of a running container:
```
$ docker run -it ubuntu ls /
```

### Command to create a volume mount:
```
$ docker volume create {volumeName}
```
> It is used by: `$ docker run -dp 127.0.0.1:3000:3000 --mount type=volume,src=volumeName,target=/etc/dirName imageName`

### Command to inspect a volume:
```
$ docker volume inspect {volumeName}
```

### Command to start a app with Docker Compose:
```
$ docker compose up
```
> -d to run in the background

### Command to stop the app
```
$ docker compose down
```
> --volumes, removes the data volume used

### Command to run one off the commands from the server:
```
$ docker compose run {command}
```

### Command to stop the services:
```
$ docker compose stop
```