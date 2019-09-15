# Prerequisites
The workshop requires that you download some applications to be able to complete the tasks on the day.
You should do these tasks before the workshop as some of the files are large.

The tasks that will be the following
* Download Java SDK
* Install one of the following IDE's, Intellij/Eclipse, IntelliJ is recommended
* Download Docker and pull in some images

## Downloading Java SDK
   
   * Visit https://adoptopenjdk.net
   
   * Select Java version 12, Hotspot and then Download.
    ![Install Java image](images/java_install.png)

   * Next follow the steps to install the package you have downloaded.
      * Windows - [Install page](https://adoptopenjdk.net/installation.html?variant=openjdk12&jvmVariant=hotspot#windows-msi)
      * Mac - [Install page](https://adoptopenjdk.net/installation.html?variant=openjdk12&jvmVariant=hotspot#macos-pkg)

## Install and IDE
The next stage is to install an IDE 

### IntelliJ - Recommended
   * Visit https://www.jetbrains.com/idea/download/#section=mac
   * Select the Ultimate or Community Edition and download. Ultimate has a 30 day trial which is nice to use if you have a free trial available. Community Edition is missing a few features from Ultimate but none that matter for this workshop and is completely free
      * Windows - Run the downloaded EXE file
      * Mac - Run the downloaded PKG file
   * Configure the SDK
      * Open intelliJ and select File -> Project Structure
      * Notice the **No SDK** which can be seen in the image
      ![Setup the SDK](images/no_sdk.png)
      * Select New -> JDK
      * Navigate to your installed Java SDK
         * Windows - c:\ProgramFiles\AdoptOpenJDK\\***version-downloaded***/Contents/Home
         * Mac - /Library/Java/JavaVirtualMachines/***version-downloaded***/Contents/Home

### Eclipse
   * Visit https://www.eclipse.org/downloads/packages/
   * Find the "Eclipse IDE for Java Developers" and download the version for you operating system
   ![Eclipse download](images/eclipse_install.png)
      * Windows - Unzip the downloaded zip folder and run the eclipse.exe file
      * Mac - Open the DMG file and eclipse will install

## Docker
We next need to install Docker. First it is necessary to create a Docker account.

* To create a Docker account visit the following website and register: https://hub.docker.com/signup

* Visit https://hub.docker.com/?overlay=onboarding
   * Follow the first step to download Docker for your operating system
   * Run the downloaded file
      * Windows run the EXE file
      * Mac run the DMG file
   * If you wish continue with the rest of the docker guide, however, it is not important

## Docker images
As part of the workshop we will require the downloading of some docker images. These can be quite large so to save time it may be simpler to download these now

   * Open a cli
      * Windows - Powershell
      * Mac - terminal
   * Execute the following commands
   ```docker
   docker pull postgres:latest
   docker pull dius/pact-broker:latest
   docker pull jpgough/api-workshop-gateway
   docker pull gradle:jdk12
   ```

# Recommended prerequisite.
We recommend installing the following software ahead of the workshop, though they are small so it is not essential

## Swagger diff

   * Download the following jar file: `wget http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/2.3.1/swagger-codegen-cli-2.3.1.jar -O swagger-codegen-cli.jar`
   * Make a note of where you have installed it as it will be used in the workshop

## A REST client
We will be making requests to the services we build so it is really useful to have a rest client. Please use any of the suggested options:
   * IntelliJ there is one available in Tools -> HTTP Client -> Test RESTful Web Service
   * For Windows or Mac [POSTman](https://www.getpostman.com/downloads/)
   * For chrome [restlet client](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en)
