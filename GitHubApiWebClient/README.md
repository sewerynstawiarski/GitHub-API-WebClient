<div style="text-align: center;">

<ins>***<h1>-REST API-</h1>***</ins>

<font size="3">The goal of the application is to allow to 
access all repositories of GitHub user, of which he/she is an owner, based on 
username. 

**Application provides list of "RepositoryDTO" objects, for each repository the response contains:**  

    1.RepositoryNoBranches  - contains:
    1.1 name  
    1.2 OwnerDTO (contains login of the owner)
    1.3 fork (boolean)
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
**1. services.unit.GitHubClientImplTest**

1.1_ testListRepositories()_  

This test uses webTestClient to simulate a connection from a user. It checks if the application answers with the correct status and body.

1.2._ testListRepositoriesNotFound()_

This test uses webTestClient to simulate a connection from a user with the wrong name of the GitHub account. It checks if the application response matches the guidelines.

**2. services.integration.GitHubClientImplIntTest**

2.1 _testListRepositoriesWithWireMock()_

This test uses wireMock to mock an external server. It checks if the application correctly deals with the answer from an external API by checking if a new object was properly created.

</div>

