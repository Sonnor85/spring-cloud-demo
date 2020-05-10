@echo off
Echo =======================================
Echo = Starting Maven build
Echo =======================================
call mvnw clean install
Echo =======================================
Echo = Starting Docker Build
Echo =======================================
for %%i in (gateway) do (
    cd %%i
    docker build -t iszell/%%i:latest .
    cd ..
)
Echo =======================================
Echo = Starting Kubernetes deployment
Echo =======================================
kubectl delete -f .kubernetes
kubectl apply -f .kubernetes
