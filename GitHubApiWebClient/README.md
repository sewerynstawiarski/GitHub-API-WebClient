<div style="text-align: center;">

<ins>***<h1>-GITHUBAPI CLIENT-</h1>***</ins>

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
Webflux    
MapStruct   
WireMock    
Tomcat

<ins>**Source API (GitHub):**</ins>  
https://developer.github.com/v3  

<ins>**The link to use after running application:**</ins>  
http://localhost:8080/user/{USERNAME}/repositories

<ins>**Testing**</ins>  

**Tests requires using a separate profile called *"GitHubApiWebClientApplicationTest"* that uses the *"application-test.properties"* file to set up a *wireMock server***

**1. IMPLEMENTATION TESTS -  services.unit.GitHubClientImplTest**

**1.1_ '*testListRepositories()*'_**

- **Purpose:** Verifying if the application can successfully retrieve a list of repositories from GitHubAPI.
- **Approach:** Using *webTestClient* to simulate user connections.
- **Assertion:** It checks if the application responds with the correct HTTP status code and body.

**1.2._ '*testListRepositoriesNotFound()*'_**

- **Purpose:** Ensures proper behavior when the user provides the wrong name of the GitHub Account.
- **Approach:** Simulate a user connection with a non-existing GitHub account name.
- **Assertion:** Confirms that the application responded according to guidelines (with a 404 code and message).

**2. INTEGRATION TESTS -  services.integration.GitHubClientImplIntTest**

**2.1 _'*listRepositoriesWithWireMockTest()*'_**

- **Purpose:** Testing application behavior when interacting with an external server.
- **Approach:** Uses *wireMock* to simulate responses from the GitHub server.
- **Assertion:** Verifying that the application process response from the external server is correct by creating and passing a new object. 
  
**2.2 _'*listRepositoriesUserNotFoundWithWireMocKTest()*'_**
- **Purpose:** Testing application behavior when the GitHub user cannot be found.
- **Approach:** Uses *wireMock* to simulate responses from the GitHub server.
- **Assertion:** Confirms the application passes *WebClientResponseException* properly.
  
**2.3 _'*listRepositoriesWithWireMocKAndWebClientTestTest()*'_**

- **Purpose:** Checking application behavior when receiving call from user.
- **Approach:** Uses *wireMock* to simulate an external server and *webTestClient* to simulate a connection from the user.
- **Assertion:** Confirms that after receiving a response from an external server, the application passes a correctly structured JSON response to the user.

**2.4 _'*listRepositoriesUserNotFoundWithWireMocKAndWebTestClientTest()*'_**

- **Purpose:** Checking application behavior when receiving request from user with the wrong username.
- **Approach:** Uses *wireMock* to simulate an external server response and *webTestClient* to simulate a connection from the user.
- **Assertion:** Confirms that after receiving a response with an error from an external server, the application passes a correct message and status response to the user.
</div>

