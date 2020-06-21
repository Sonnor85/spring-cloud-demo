# spring-cloud-demo
A microservices demo project for Kubernetes with Kubernetes service discovery, configuration and load balancing.
Used projects:
* Spring-Boot
* Spring-Boot-Actuator
* Spring-Cloud-Kubernetes
* Spring-Cloud-Gateway
* Spring-Security
* SpringDoc-OpenAPI
* JJWT

Load balancing is done via Kubernetes services so no client side load balancing is used.
Gateway routes are set accordingly.
URL pattern for microservice calls is `http://localhost:8080/api/v1/<kubernetes service name>/<rest of the API path>`
Health check probes are done by Spring-Boot-Actuator.
Common configuration of the whole application is in a Kubernetes `ConfigMap`.
Spring `application.yml`s contain only the project specific configuration which should not be changed for a deployed
application. 
## Prerequisites
Docker desktop for Windows installed and Kubernetes enabled
## Try out
Execute `build.bat`. That will compile the whole project using a Maven Wrapper, build the Docker images and deploy the 
whole project to Kubernetes in the namespace `springdemo`.
## `build.bat` options
### `nobuild`
Skips Maven build but runs Docker build and does Kubernetes (re)deployment as well.
### `nodocker`
Skips Docker build image build.
### `nodeploy`
Does Maven and Docker builds only and skips Kubernetes.
## Removing from Kubernetes (undeploying)
`kubectl --namespace springdemo delete -f .kubernetes`
## Directories and files
### .kubernetes
The deployment descriptors for Kubernetes
#### cluster-config.yml
The configuration properties for the whole application
#### deployment.yml
Deployment descriptor:
* Gateway App
* Gateway public LoadBalancer
** port 8080: application port
** port 8081: management (actuator) port 
* Authentication App
* Authentication internal load balancer (ClusterIP)
#### discovery-cluster-role.yml
Kubernetes Role and Role Binding definitions that allows service discovery to work.
### gateway
A Spring-Cloud-Gateway implementation that routes every `http://localhost:8080/api/v1/<serviceId>/*` request to the 
appropriate service. For example  `http://localhost:8080/api/v1/authentication/authenticate` will be routed to the
cluster internal `http://authentication/api/v1/authenticate` address.
In addition, it serves as an API documentation aggregator using the SpringDoc-OpenAPI project, published at
`http://localhost:8080/swagger-ui.html`
### authentication
A simple, dummy username/password authentication that generates a JWT token to be used with other APIs. It accepts every
username and password combination.
Usage: 
`curl -X POST "http://localhost:8080/api/v1/authentication/authenticate" 
      -H "accept: */*"
      -H "Content-Type: application/json"
      -d "{\"username\":\"user\",\"password\":\"AsffDsE_344\"}"`
# API documentation
Navigate your browser to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
It will open Swagger UI and the API documentation dropdown will be populated based on the running services.
# Actuator API
Spring Boot Actuator endpoints (for the gateway) are exposed at port `8081`. For example gateway routes are available at
[http://localhost:8080/actuator/gateway/routes](http://localhost:8080/actuator/gateway/routes).
# TODO
API documentation links currently does not include the microservice name so Swagger UI calls will fail
(they call `http://localhost:8080/api/v1/authenticate` instead of `http://localhost:8080/api/v1/authentication/authenticate`)
