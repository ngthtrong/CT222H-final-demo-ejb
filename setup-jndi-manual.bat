@echo off
echo ========================================
echo Creating Manual JNDI Mapping for Remote EJB
echo ========================================
echo.

REM Set GlassFish home directory (adjust this path as needed)
set GLASSFISH_HOME=C:\glassfish7

REM Change to GlassFish bin directory
cd /d "%GLASSFISH_HOME%\bin"

echo Creating custom JNDI resource...
asadmin create-custom-resource --restype com.example.api.MySessionBeanRemote --factoryclass com.sun.enterprise.naming.impl.SerialInitContextFactory ejb/MySessionBeanRemote

echo.
echo Creating JNDI binding for EJB...
asadmin create-jndi-resource --jndiname ejb/MySessionBeanRemote --target server

echo.
echo Listing all JNDI resources...
asadmin list-jndi-entries

echo.
echo ========================================
echo Manual JNDI setup completed!
echo You can now use: ejb/MySessionBeanRemote
echo ========================================
pause
