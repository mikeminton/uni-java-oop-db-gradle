@echo off
cls

REM This script builds the project and runs the application using Gradle

REM Navigate to the project directory (optional, if you're not already there)
cd /d "C:\Users\MichaelMinton\Dropbox\SRC-CODE\repos\uni-java-oop-db-gradle"

REM Run the Gradle build
echo Building the project...
call gradlew build
if %errorlevel% neq 0 (
    echo Build failed, exiting script.
    pause
    exit /b %errorlevel%
)

REM Run the Gradle runApp task
echo Running the application...
call gradlew runApp
if %errorlevel% neq 0 (
    echo Application failed to run, exiting script.
    pause
    exit /b %errorlevel%
)

echo Script completed successfully.
pause
