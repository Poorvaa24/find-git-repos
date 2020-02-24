## find-git-repos
This service will allow user to enter any word as an input and will get the list of PHP and JavaScript repositories from GitHub, where the entered word is part of repository name.

Each repository in the list will contain:

· Repository name

· Link to repository on GitHub

· Some information about repository owner

## Requirements to run the project:

1. Server: Spring Boot v2.2.4.RELEASE
2. Client: Vue.js @vue/cli 4.2.2

## Required Ports:

1. Server : 8080
2. Client: 3000

## Steps to run the project.
1. Download the project folder SpringBootVueProject.
2. To run the backend server, go into the server folder and use the below command. The server will start on port 8080.

    ### mvn spring-boot:run

3. To run the client, go to the client folder and run the below command. The client will start on port 3000.

    ### yarn serve

4. Go to the url http://localhost:3000/ and you will find the home page of the project.

![alt text](/SpringBootVueProject/screenshots/homepage.png)

5. User can type any keyword and all the github repositories of PHP and Javascipt containing that keyword will be returned.

![alt text](/SpringBootVueProject/screenshots/repos.png)



