# Stack exchange api manipulation
Simple app to communicate with public API of StackOverflow team (fetching and print output in console)

# App Structure
Based on N-Tier architecture:
1. Layer of Controllers.
2. Layer of Service.

# Technologies
* Java 17
* Tomcat 9.0.65
* Gradle 7.6 
* Retrofit 2.9.0 
* Spring Boot 3.0.1

# Quick start
1) Clone this repository
2) Start gradle build with a command **gradle clean build**

# Main endpoints
* **http://localhost:8080/api/users** - to fetch users (standard page elements to show: 20)
* **http://localhost:8080/api/users/{ids}/tags** - to fetch user's tags, where ```{ids}``` is user ids separated by semicolon
* **http://localhost:8080/api/users/{ids}/answers** - to fetch user's answers, where ```{ids}``` is user ids separated by semicolon
* **http://localhost:8080/api/users/{ids}/questions** - to fetch user's questions, where ```{ids}``` is user ids separated by semicolon
___
Some filters to changing between pages:
append ```?page={number}``` or ```?pagesize={number}``` or both ```page={number}&pagesize={number}``` to the end of URL for switching pages

# Stack Exchange API Throttling Policy
```
Every application is subject to an IP based concurrent request throttle. If a single IP is making 
more than 30 requests a second, new requests will be dropped. The exact ban period is subject to 
change, but will be on the order of 30 seconds to a few minutes typically. Note that exactly what 
response an application gets (in terms of HTTP code, text, and so on) is undefined when subject to 
this ban; we consider > 30 request/sec per IP to be very abusive and thus cut the requests off 
very harshly.
```