@echo off
setlocal
echo ====================================================================
echo   ShopSphere - Tomcat 11 Deployment Script
echo ====================================================================

set TOMCAT_HOME=C:\Users\USER\Downloads\apache-tomcat-11.0.25-windows-x64\apache-tomcat-11.0.25
set WAR_SOURCE=%~dp0web\target\shopsphere.war

if not exist "%WAR_SOURCE%" (
    echo [ERROR] WAR file not found at %WAR_SOURCE%
    echo Please run build_all.bat first.
    pause
    exit /b 1
)

if not exist "%TOMCAT_HOME%" (
    echo [ERROR] Tomcat not found at %TOMCAT_HOME%
    pause
    exit /b 1
)

echo Deploying shopsphere.war to %TOMCAT_HOME%\webapps...
copy /Y "%WAR_SOURCE%" "%TOMCAT_HOME%\webapps\"

echo.
echo [SUCCESS] Deployed successfully!
echo Web Application URL: http://localhost:8080/shopsphere/
echo.
pause
