# 1. Build Executable .JAR file ( 2 steps, configuring & creating )
 - JavaFX SDK official download (https://gluonhq.com/products/javafx/)    
###  1.1 Configuring .JAR to be Created
    1. Go to File -> Project Structure -> (CTRL+ALT+SHIFT+S)
    2. Select Artifacts
    3. Click +
    4. Select JAR → From modules with dependencies...
    5. Select the class Launcher class
    6. Click OK
    7. Click the + below the Output Layout tab and select Directory Content
    8. Select the C:\bin\Java\javafx-sdk-11.0.1\bin folder
    9. Click OK twice
    
###  1.2 Creating the .Jar 
    1. Go to Build → Build Artifacts
    2. Select the Build action in the pop-up menu
    3. The .jar file should be located in a folder in the out\artifacts folder of your project
# 2. Creating .EXE with Launch4j...
    ###### Basic
    1. Create Output folder & put Output file path & name (don't forget end with .exe)
    2. Put Jar file path
    3. Select Icon path
    4. Enter error title

    ###### Single instance
    5. Mark checkbox
    6. Fill Mutex name

    ###### JRE
    7. In Output location folder create folder 'jre' and copy 'jre1.8.0_291' -- bin/lib folders
    8. In Bundled JRE paths write location of jre -- 'jre' 
    9. Fill Min & Max JRE version ( 1.8.0_111 ---- 1.9.0 )
    10. Select 'Only use private JDK runtimes'

    ###### Version info
    11. Mark checkbox & fill fields:
    12. File version ( *.0.0.0 must be )
    13. File description
    14. Copyright
    15. Product version ( *.0.0.0 must be )
    16. Product name
    17. Internal name
    18. Original filename (.exe)

    ###### Messages
    19. Mark checkbox
    
    ###### In Menu Bar
    20. Save config file
    21. Compile project
   - Successfully created!!!
