@echo off
set "CATALINA_HOME=C:\Users\USER\Downloads\apache-tomcat-11.0.25-windows-x64\apache-tomcat-11.0.25"
setlocal
echo ====================================================================
echo   ShopSphere - Start Apache Tomcat 11 Server
echo ====================================================================

set TOMCAT_HOME=C:\Users\USER\Downloads\apache-tomcat-11.0.25-windows-x64\apache-tomcat-11.0.25

if not exist "%TOMCAT_HOME%\bin\startup.bat" (
    echo [ERROR] Tomcat startup.bat not found at %TOMCAT_HOME%\bin\
    pause
    exit /b 1
)

echo Starting Tomcat in a new window...
start "Apache Tomcat 11" "%TOMCAT_HOME%\bin\startup.bat"

echo.
echo Tomcat is starting up...
echo Once started, open your browser at:
echo http://localhost:8080/shopsphere/
echo.
pause
