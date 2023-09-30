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