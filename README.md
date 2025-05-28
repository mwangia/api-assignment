# Prerequisites

- Docker v20.10.14 or later
- MiniKube v1.36.0
- Java 17 or later
- Mac Terminal
 

# How to run the application
- Clone the Git project to your computer.
- In the Terminal, navigate to the `kubernetes` folder and run
  - `kubectl apply -f deployment.yaml`
  - `kubectl apply -f service.yaml`
- To get the base application url, run `minikube service myapp-service --url`. This will return a url that looks something like `http://127.0.0.1:54035`
- The base endpoint url is the url from above appended with `/v1/customers` for example `http://127.0.0.1:54035/v1/customers`
- To test if the app is running, hit the `/actuator/health` endpoint. You should see a response like `{"status":"UP"}`.

# Step 1: Create CRUD API
Created the REST API using Spring Boot and an H2 in-memory database. Note: since I used an in-memory db, every time the app is restart the database data is cleared.

# Step 2: Integration/Acceptance Tests
I implemented both unit tests and integration tests. To run the tests, navigate to the root folder and run:
- `./gradlew build` to run all the tests.
- `./gradlew test` to run only unit tests.
- `./gradlew integrationTest` To run only integration tests.

I would automate this for CI/CD by ensuring all the tests run whenever code is pushed to a feature branch, whenever a pull request is created and whenever a pull request is merged into a branch. 

# Step 4: Containerization
To run the application without using Kubernetes, navigate to the root folder(where the Dockerfile is) and run
1.  `docker build -t mwangia/api-assignment:latest .` then run:
2.  `docker run -p 9999:9999 mwangia/api-assignment:latest`

The application endpoint will be available at `http://localhost:9999/v1/customers`

# Step 5: Kubernetes
Added service and deployment config YAML files for deploying the app to Kubernetes. I tested locally using MiniKube.

# Step 6: CI/CD
When code is pushed to a branch the CI/CD pipeline would run a build to compile the code and generate a runnable instance of the app. After this, all the tests would run and if any test fails the pipeline would not proceed.
If the tests pass the next stage would be a code analysis check to identify any security vulnerabilities and coding defects. The next stage would be deployment to the environments.

## Build & Test
This stage is automated and would entail:
- downloading and caching the Gradle dependencies.
- building the JAR using `./gradlew build`.
- running the tests.
- uploading the dockerized JAR image to a remote container repository like Dockerhub or AWS Elastic Container Repository.

## Code Analysis
This stage would also run automatically and would kick off static code analysis tools.

## Deployment
This stage entails getting the Docker image and deploying it to the server where it will run.

Deployment to the development environment can be automated so that developer code changes are available to other developers/testers before being shipped to production. 
Deployment to production can be manual (e.g need manual approval like clicking a button to deploy) so that only items that have been thouroghly tested in the dev environment are deployed to production.

### Examples
#### Feature Branch
When a developer wants to work on a task they create a feature branch from the `main` branch. When they push changes to this feature branch the following stages are triggered: build->test->code-analysis.

#### Pull Request
After the developer has finished working on the task and is ready to merge their changes, they create a pull request. This would trigger the following stages in the pipeline: build->test->code-analysis.

#### Merge Branches
Once the pull request is approved, it is merged to the `main` branch. This would trigger the following actions in the pipeline: build->test->code-analysis->automatic deployment to dev environment.
Nothing would be deployed to production without manual approval.

# Step 7: App Integration
There is a client application in the `api-consumer` folder. To run it, navigate to the root folder and run ` ./gradlew :api-consumer:run --args=<endpoint_url>` there `endpoint_url` is the Docker or Kubernetes url. For example:
`./gradlew :api-consumer:run --args="http://localhost:9999/v1/customers` or `./gradlew :api-consumer:run --args="http://127.0.0.1:59174/v1/customers`.

This will execute several API requests and print the output in the console.

