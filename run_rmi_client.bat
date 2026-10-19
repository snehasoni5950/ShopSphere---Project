@echo off
setlocal
echo ====================================================================
echo   ShopSphere - Java RMI Client (Lookup & Remote Calls)
echo ====================================================================

set MVN_BIN=C:\Users\USER\Downloads\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin\mvn.cmd

call "%MVN_BIN%" exec:java -pl rmi -Dexec.mainClass="com.shopsphere.rmi.client.RmiClientMain"

pause
