@echo off
if "%1"=="nobuild" goto :nobuild
echo =======================================
echo = Starting Maven build
echo =======================================
call mvnw clean install
goto buildend
:nobuild
shift
echo =======================================
echo ! Skipping Maven build
echo =======================================
:buildend
if "%1"=="nodocker" goto :nodocker
echo =======================================
echo = Starting Docker Build
echo =======================================
for %%i in (gateway, authentication) do (
    cd %%i
    docker build -t iszell/%%i:latest .
    cd ..
)
goto dockerend
:nodocker
shift
echo =======================================
echo ! Skipping Docker build
echo =======================================
:dockerend
if "%1"=="nodeploy" goto :nodeploy
echo =======================================
echo = Starting Kubernetes deployment
echo =======================================
kubectl delete -f .kubernetes --namespace=springdemo
kubectl delete namespace springdemo
kubectl create namespace springdemo
kubectl apply -f .kubernetes --namespace=springdemo
goto deployend
:nodeploy
shift
echo =======================================
echo ! Skipping Kubernetes deployment
echo =======================================
:deployend
