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
mvn package
mvn exec:java -Dexec.mainClass="{groupId.mainClass}" -D exec.cleanupDaemonThreads=false
```

# Server-side programming and application servers (Tomcat)

We can create a new **Jakarta EE aplication**, based on the Web Profile, using InteliJ. This will create a new project with the necessary dependencies.