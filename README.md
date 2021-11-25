# Problem
The entire human world works according to a schedule: meetings, visits, departures, arrivals and even wake-ups by an alarm...
But there is nothing wrong with that. First of all, it allows you to efficiently use resources such as time and work.
As second: people really like to keep everything under control.

And it will not be a secret for you that at school or maybe at a university or wherever, students have a schedule according to which they study.
But what if students need something with information like tomorrow's subjects, the teacher's name and audience in which will the lecture be?

# Summary

It's a Back-end REST API application. The application provides CRUD operations for such entities:
Student, StudentGroup, Course, Lecture, Audience.
You can either work with the entities or with schedule endpoint.


# Requirements:

### Installed:
- [JDK 1.8+](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/)
- [Git](https://git-scm.com/doc)
- [PostMan](https://www.postman.com/downloads/)

# Steps

### 1. Get source code

Clone source code from git
> $ git clone https://github.com/staffsterr2000/student-schedule-rest

Go to the project root folder
> $ cd student-schedule-rest

### 2. Compile (with Maven)

Compile application by running

> $ mvn clean install

### 3. Run (with docker-compose)

Build and start the containers by running

> $ docker-compose -f docker-compose.yml --env-file docker.env up

Remove the containers (optional)

> $ docker-compose -f docker-compose.yml --env-file docker.env down

### 4. Using (with Postman)

Copy one from the given links, paste values in fields like this {some_id}, choose request method and send request with Postman:


  * To interact with Schedule:
    - localhost:8080/api/v1/schedule/{student_id}?date={some_date} (GET)
    - ![Full field Student request example](/images/schedule_link_example.PNG)
      

  * To interact with Student:
    - localhost:8080/api/v1/student/ (GET)
    - localhost:8080/api/v1/student/ (POST) + BODY
    - localhost:8080/api/v1/student/{student_id} (GET)
    - localhost:8080/api/v1/student/{student_id} (DELETE)
    - localhost:8080/api/v1/student/{student_id} (PUT) + BODY
    - ![Full field Student request example](/images/student_full_fields_example.PNG)
   

  * To interact with StudentGroup:
    - localhost:8080/api/v1/sgroup/ (GET)
    - localhost:8080/api/v1/sgroup/ (POST) + BODY
    - localhost:8080/api/v1/sgroup/{sgroup_id} (GET)
    - localhost:8080/api/v1/sgroup/{sgroup_id} (DELETE)
    - localhost:8080/api/v1/sgroup/{sgroup_id} (PUT) + BODY
    - ![Full field StudentGroup request example](/images/sgroup_full_fields_example.PNG)
      
    
  * To interact with Course:
    - localhost:8080/api/v1/course/ (GET)
    - localhost:8080/api/v1/course/ (POST) + BODY
    - localhost:8080/api/v1/course/{course_id} (GET)
    - localhost:8080/api/v1/course/{course_id} (DELETE)
    - localhost:8080/api/v1/course/{course_id} (PUT) + BODY
    - ![Full field Course request example](/images/course_full_fields_example.PNG)
    

  * To interact with Lecture:
    - localhost:8080/api/v1/lecture/ (GET)
    - localhost:8080/api/v1/lecture/ (POST) + BODY
    - localhost:8080/api/v1/lecture/{lecture_id} (GET)
    - localhost:8080/api/v1/lecture/{lecture_id} (DELETE)
    - localhost:8080/api/v1/lecture/{lecture_id} (PUT) + BODY
    - ![Full field Lecture request example](/images/lecture_full_fields_example.PNG)
    

  * To interact with Audience:
    - localhost:8080/api/v1/audience/ (GET)
    - localhost:8080/api/v1/audience/ (POST) + BODY
    - localhost:8080/api/v1/audience/{audience_id} (GET)
    - localhost:8080/api/v1/audience/{audience_id} (DELETE)
    - localhost:8080/api/v1/audience/{audience_id} (PUT) + BODY
    - ![Full field Audience request example](/images/audience_full_fields_example.PNG)
