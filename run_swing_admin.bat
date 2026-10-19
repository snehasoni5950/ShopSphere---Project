@echo off
setlocal
echo ====================================================================
echo   ShopSphere - Java Swing Desktop Admin Application
echo ====================================================================

set JAR_PATH=%~dp0swing-admin\target\shopsphere-swing-admin-1.0.0.jar

if not exist "%JAR_PATH%" (
    echo [ERROR] Shaded JAR not found at %JAR_PATH%
    echo Please run build_all.bat first.
    pause
    exit /b 1
)

echo Launching Java Swing Desktop Admin Portal...
java -jar "%JAR_PATH%"

pause
