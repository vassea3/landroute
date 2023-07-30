**Land Route instructions**

Land route is a simple spring boot application which calculates the route from one country to another one. It has one endpoint for this @GetMapping("/routing/{origin}/{destination}"). Path variable origin represents the country from which we want to create the route and destination path variable represents the destination country for our route.

The application runs on port 8085, it is configurable from application.properties file. 

Next is represented the full path of a get endpoint to obtain the route between 2 countries: http://localhost:8085/routing/CZE/ITA

And this is the result:

{
  "route": [
    "CZE",
    "AUT",
    "ITA"
  ]
}

For starting the application you do not need any special arguments. Users should have the java 18 for using this app.

For starting application from the jar I’m using next command:
java -jar landroute-0.0.1-SNAPSHOT.jar

landroute-0.0.1-SNAPSHOT.jar is the application jar file.
