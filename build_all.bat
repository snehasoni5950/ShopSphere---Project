@echo off
setlocal
echo ====================================================================
echo   ShopSphere - Full Maven Clean Package
echo ====================================================================

set MVN_BIN=C:\Users\USER\Downloads\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd

call "%MVN_BIN%" clean package -DskipTests

if %ERRORLEVEL% equ 0 (
    echo.
    echo ====================================================================
    echo   BUILD SUCCESSFUL! All 5 modules packaged:
    echo   - Core JAR:        core\target\shopsphere-core-1.0.0.jar
    echo   - Web WAR:         web\target\shopsphere.war
    echo   - Swing Shaded:    swing-admin\target\shopsphere-swing-admin-1.0.0.jar
    echo   - Network JAR:     network\target\shopsphere-network-1.0.0.jar
    echo   - RMI JAR:         rmi\target\shopsphere-rmi-1.0.0.jar
    echo ====================================================================
) else (
    echo.
    echo [ERROR] Build failed. Please check the maven errors above.
)

pause
