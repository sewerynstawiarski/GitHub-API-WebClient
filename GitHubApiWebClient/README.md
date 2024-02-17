<div style="text-align: center;">

<ins>***<h1>-REST API-</h1>***</ins>

<font size="3">The goal of the application is to allow to 
access all repositories of GitHub user, of which he/she is an owner, based on 
username. 

**Application provides list of "RepositoryDTO" objects, for each repository the response contains:**  

    1.RepositoryNoBranches  - contains:
    1.1 name  
    1.2 OwnerDTO (contains login of the owner)
    2.List of BranchDTO, each one contains:
    2.1 name
    2.2 CommitDTO (contains sha of the last made commmit)


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
http://localhost:8080/user/{USERNAME}/repositories

<ins>**Testing**</ins>  
1._testGetRepositories()_  
test checks if WebClient correctly connects to API and retrieve list of Repositories and Branches for the user. Response is printed in the terminal.    
2._testGetBranches()_  
test checks if WebClient correctly connects and retrieve list of branches with sha of the last commit, from GitHub API. Response is printed in the terminal.  


</div>

