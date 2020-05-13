# spring-cloud-demo
A microservices demo project for Kubernetes with Kubernetes service discovery, configuration and load balancing.
Used projects:
* Spring-Boot
* Spring-Boot-Actuator
* Spring-Cloud-Kubernetes
* Spring-Cloud-Gateway
* SpringDoc-OpenAPI
* JJWT 
## Prerequisites
Docker desktop for Windows installed and Kubernetes enabled
## Try out
Execute `build.bat`. That will compile the whole project using a Maven Wrapper, build the Docker images and deploy the 
whole project to Kubernetes in the namespace `springdemo`.
## `build.bat` options
### `build.bat nobuild`
Skips Maven and Docker builds and does Kubernetes (re)deployment only.
### `build.bat nodeploy`
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
`curl -X POST "http://localhost:8080/api/v1/authenticate" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"username\":\"user\",\"password\":\"AsffDsE_344\"}"`
