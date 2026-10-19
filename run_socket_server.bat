@echo off
setlocal
echo ====================================================================
echo   ShopSphere - java.net Socket Inventory Server (Port 8088)
echo ====================================================================

set MVN_BIN=C:\Users\USER\Downloads\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd

call "%MVN_BIN%" exec:java -pl network -Dexec.mainClass="com.shopsphere.network.server.ShopSphereInventoryServer"

pause
