@echo off
echo === Credit Simulator (Windows) ===
if "%~1"=="" (
    java -jar target\credit_simulator-1.0-SNAPSHOT.jar
) else (
    java -jar target\credit_simulator-1.0-SNAPSHOT.jar "%~1"
)
