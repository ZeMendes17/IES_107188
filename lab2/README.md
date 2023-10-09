# Server-side programming with servlets

## Java Servlet
Technology to design and deploy dynamic web pages using the Java Programming Language. It implements a typical servlet in the client-server architecture, and the Servlet lives on the server-side.

A servlet must be deployed into a (multithreaded) Servlet
Container to be usable.

When an application running in a web server
receives a request, the Server hands the request to
the Servlet Container which in turn passes it to the
target Servlet.
Servlet Container is a part of the usual set of
services that we can find in Java Application Server.

### Implementation of Servlet using *Jetty*

After creating a **Maven Project**, we need to add the dependencies to *pom.xml*:

```
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-server</artifactId>
    <version>9.2.15.v20160210</version>
</dependency>
```
```
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-servlet</artifactId>
    <version>9.2.15.v20160210</version>
</dependency>
```

#### Simple implementation

```
public static void main(String[] args) throws Exception {
    Server server = new Server(8680);
    try {
        server.start();
        server.dumpStdErr();
            server.join();
        } catch (Exception e) {           
        e.printStackTrace();
        }  
    }
}
```
> **Note**: It won’t do anything useful work as there are no handlers.

#### Embedded Jetty Server with ServletHandler

* Example:
```
public static class HelloServlet extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>New Hello Simple Servlet</h1>"); 
    } 
}
```
> **Note**: We can use the request object to get the parameters and the response object to send the response back to the client.

* Example:
```
response.getWriter().println("<h1>" + msg + "</h1>");
```

#### To run:
```
$ mvn package
$ mvn exec:java -Dexec.mainClass="{groupId.mainClass}" -D exec.cleanupDaemonThreads=false
```

# Server-side programming and application servers (Tomcat)

We can create a new **Jakarta EE aplication**, based on the Web Profile, using InteliJ. This will create a new project with the necessary dependencies.
With the dependecies, also comes a *plugin* in order to generate the **WAR** file instead of the **JAR** file.
> **Note**: The WAR file is the file that we need to deploy in the application server.

## Tomcat
We can creating a docker-compose file to run the Tomcat server:
```
version: '3.8'
services:
  tomcat-10-0-11-jdk17:
    image: tomcat:10.0-jdk17
    ports:
      # expose tomcat port 8080(container) on host as port 8888(host)
      - "8888:8080"
      # expose java debugging port 5005 on host as port 5005  (HOST:CONTAINER)
      - "5005:5005"
    command: "catalina.sh run"
    volumes:
      - "./target:/usr/local/tomcat/webapps"
    environment:
      JAVA_OPTS: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```
> **Note**: We can use the `docker-compose up` command to run the server

While the server is running, we can access the manager page in the browser: `http://127.0.0.1:8888/JakartaWebStarter-1.0-SNAPSHOT/hello-servlet`

# Spring Boot

It is useful to get started with minimum effort and create stand-alone, production-grade applications.

### How to Create:

We can use the site: **https://start.spring.io/** to create a new (maven-supported String Boot) project, in this case, for our web app, thus we need to add the **Spring Web** dependency. This templates contain a collection of all
the relevant transitive dependencies that are needed to start a particular functionality and will simplify the setup of the POM.
Finally just download the template.

After it we should be able to build your application using the regular Maven commands.

It also includes a **Maven wrapper script (mvnw)**.

### How to Run:

```
$ mvn install -DskipTests && java -jar target\webapp1-0.0.1-SNAPSHOT.jar
```

OR

```
$ ./mvnw spring-boot:run
```

## Building a Simple Application to Serve Web Content

Go to **https://start.spring.io/** once more, but this time we will add the dependencies: **Spring Web**, **Thymeleaf**, and **Spring Boot DevTools**.

In Spring’s approach to building web sites, HTTP requests are handled by a **controller**. We can easily identify the controller by the **@Controller** annotation.

In the following example, **GreetingController** handles **GET** requests for **/greeting** by returning the name of a **View** (in this case, greeting). A **View** is responsible for rendering the HTML content.

```
@Controller
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting"; 
    }
}
```

> The **@GetMapping** annotation ensures that **HTTP GET requests** to **/greeting** are mapped to the **greeting()** method.

> **@RequestParam** binds the value of the query string parameter name into the name parameter of the **greeting()** method. This query string parameter is not required. If it is absent in the request, the *defaultValue* of World is used. The value of the name parameter is added to a **Model object**, ultimately making it accessible to the **view template**.

> The implementation of the method body relies on a view technology (in this case, **Thymeleaf**) to perform *server-side rendering of the HTML*. **Thymeleaf** parses the greeting.html template and evaluates the **th:text** expression to render the value of the **${name}** parameter that was set in the controller.

> **Note**: The **greeting.html** template should be placed in the **src/main/resources/templates** directory.


### Spring Boot Devtools

A common feature of developing web applications is coding a change, restarting your application, and refreshing the browser to view the change. This entire process can eat up a lot of time. To speed up this refresh cycle, Spring Boot offers with a handy module known as **spring-boot-devtools**. Spring Boot Devtools:
- Enables hot swapping.
- Switches template engines to disable caching.
- Enables LiveReload to automatically refresh the browser.
- Other reasonable defaults based on development instead of production.

### Run the Application

By default, Spring Initializr generates a main class.

```
@SpringBootApplication
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}
```

**@SpringBootApplication** is a convenience annotation that adds all of the following:
- **@Configuration**: Tags the class as a source of bean definitions for the application context.
- **@EnableAutoConfiguration**: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if **spring-webmvc** is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a **DispatcherServlet**.
- **@ComponentScan**: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.

> **Note**: The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.

All we have to do know is **Build an executable JAR**:
```
$ ./mvnw spring-boot:run
```
OR
```
$ ./mvnw clean package
$ java -jar target/gs-serving-web-content-0.1.0.jar
```

### Test the Application

Now that the web site is running, visit http://localhost:8080/greeting, where you should see:
```
Hello, World!
```

Also, if we provide a name query string parameter, we can customize the greeting with an optional name. For example, http://localhost:8080/greeting?name=User will render a page like the following:
```
Hello, User!
```

### Add a Home Page

Static resources, including HTML and JavaScript and CSS, can be served from your Spring Boot application by dropping them into the right place in the source code. By default, Spring Boot serves static content from resources in the classpath at **/static** (or /public).

The **index.html resource is special** because, if it exists, it is used as a "`welcome page,"serving-web-content/ which means it is served up as the root resource (that is, at `http://localhost:8080/).

> **Note**: Because we added the **Desvtools**, all we have to do is restart the server and the changes will be applied.

### To change the port:

We can change the port in the **application.properties** file:
```
server.port=8081
```

### Note:

The implementation of Spring MVC relies on the Servlets engine, however, you do not need to
“see” them. The abstraction layers available will provide the developer with more convenient, higher-
level interfaces.

## Building a RESTful Web Service

Now we are going to add a REST endpoint to our application. This will return a JSON representation of a greeting, rather than a HTML page.

### Create a Resource Representation Class

The service will handle **GET** requests for **/greetingREST**, optionally with a name parameter in the query string. The **GET** request should return a **200 OK** response with JSON in the body that represents a greeting. It should resemble the following output:

```
{
    "id": 1,
    "content": "Hello, World!"
}
```

> **Note**: The **id** field is a unique identifier for the greeting, and **content** is the textual representation of the greeting.

To model the greeting representation, create a resource representation class. To do so, provide a Java record class for the **id** and **content** data.

```
public record Greeting(long id, String content) {}
```

> **Note**: This application uses the Jackson JSON library to automatically marshal instances of type Greeting into JSON. Jackson is included by default by the web starter.

### Create a Resource Controller

Just like the **Controller** shown before, we can create a **REST Controller**,
using the **@RestController** annotation:

```
@RestController
public class GreetingRESTController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greetingREST")
	public GreetingREST greetingREST(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new GreetingREST(counter.incrementAndGet(), String.format(template, name));
	}
}
```

> **Note**: The implementation of the method body creates and returns a new **GreetingREST** object with id and content attributes based on the next value from the counter and formats the given name by using the greetingREST template.



A key difference between a traditional MVC controller and the RESTful web service controller shown earlier is the way that the HTTP response body is created. Rather than relying on a view technology to perform server-side rendering of the greeting data to HTML, this RESTful web service controller populates and returns a Greeting object. The object data will be written directly to the HTTP response as JSON.

### Test the Service

**Curl** is a command-line tool for transferring data and supports HTTP. To test the service we can use the following command:

```
$ curl localhost:8081/greetingREST
``` 

The output should resemble the following:

```
{"id":1,"content":"Hello, World!"}
```

We can provide a **name** query string parameter as follows:

```
$ curl 'localhost:8081/greetingREST?name=User'
```

The output should resemble the following:

```
{"id":2,"content":"Hello, User!"}
```

We can also use the **-o** flag to save the output to a file:

```
$ curl 'localhost:8081/greetingREST?name=User' -o greetingUser.json
```