# Underdog Backend Coding Challenge

## Persistence
The application will use the embedded H2 relational database to persist the data from the CBS API for further processing.
The H2 console can be accessed via [localhost:8080/h2-console][1] once the application is running.

[1]: http://localhost:8080/h2-console/login.jsp?jsessionid=93f876160f9ec0bab4e5bb71f97de0d2

## How to Run
Run the application using Gradle with the following command:
```bash
./gradlew bootRun
```
You should see this pop up if the application start up correctly:
```text
2021-03-25 14:38:57.679  INFO 30376 --- [           main] d.n.p.PlayerBackendApplication           : Started PlayerBackendApplication in 2.142 seconds (JVM running for 2.381)
2021-03-25 14:39:06.327  INFO 30376 --- [nio-8080-exec-2] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2021-03-25 14:39:06.327  INFO 30376 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2021-03-25 14:39:06.328  INFO 30376 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
```

## How to Test
I have included a postman collection that can be run against the API and it is kept in the `/postman` folder in the root
directory.

## Future Improvement
* Testing - testing really needs to be done but is not implemented right now for the sake of time.
* GraphQL - GraphQL would make a good replacement for the searching that takes place in part two. Again, this is not implemented 
for the sake of time.