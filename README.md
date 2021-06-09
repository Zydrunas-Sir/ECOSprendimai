1. Build Executable .JAR file ( 2 steps, configuring & creating )
    JavaFX SDK official download ( https://gluonhq.com/products/javafx/ )
1.1 #### Configuring .JAR to be Created  ####

Go to File -> Project Structure -> (CTRL+ALT+SHIFT+S)
Select Artifacts
Click +
Select JAR → From modules with dependencies...
Select the class Launcher class
Click OK
Click the + below the Output Layout tab and select Directory Content
Select the C:\bin\Java\javafx-sdk-11.0.1\bin folder
Click OK twice

1.2 #### Creating the Jar  ####

Go to Build → Build Artifacts
Select the Build action in the pop-up menu
The .jar file should be located in a folder in the out\artifacts folder of your project
