<div style="text-align: center;">

<ins>***<h1>-REST API-</h1>***</ins>

<font size="3">The goal of the application is to allow to 
access all repositories of GitHub user based on 
username.  

**Application provides list of "Repository" objects, for each repository the response contains:**  
1.name  
2.Owner (object containing login of the owner)  
3.List of Branches, for each branch:  
1.1.name  
1.2 Commit (object containing sha of last commit)  

<ins>_Content is provided in json format and requires header "Accept: application/json" with GET request._</ins>  

<ins>**Used technologies:**</ins>  
Spring Boot 3.2.2  
Java 21  

<ins>**Dependencies:**</ins>  
Lombok  
Awaitility  
Webflux    

<ins>**Source API (GitHub):**</ins>  
https://developer.github.com/v3  

<ins>**The link to use after running application:**</ins>  
http://localhost:8080/repositories/{username}

<ins>**Testing**</ins>  
1._testGetRepositories()_  
test checks if WebClient correctly connects to API and retrieve list of Repositories for the user. Response is printed in the terminal.    
2._testGetBranches()_  
test checks if WebClient correctly connects and retrieve list of branches with sha of the last commit, from GitHub API. Response is printed in the terminal.  


</div>

