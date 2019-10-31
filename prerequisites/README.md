# Prerequisites
The workshop requires that you download some applications to be able to complete the tasks on the day.
You should do these tasks before the workshop as some of the files are large.

The tasks that will be the following
* Download Java SDK
* Install one of the following IDE's, Intellij/Eclipse, IntelliJ is recommended
* Download Docker and pull in some images
* Download the baseline project for the labs

## Downloading Java SDK
   
   * Visit https://adoptopenjdk.net
   
   * Select Java version 11, Hotspot and then Download.
    ![Install Java image](images/java_install.png)

   * Next follow the steps to install the package you have downloaded.
      * Windows - [Install page](https://adoptopenjdk.net/installation.html?variant=openjdk11&jvmVariant=hotspot#windows-msi)
      * Mac - [Install page](https://adoptopenjdk.net/installation.html?variant=openjdk11&jvmVariant=hotspot#macos-pkg)

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
         * Windows - c:\Program Files\AdoptOpenJDK\\***version-downloaded***
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

* Next depending on your system you will need to choose the Docker version to install

   * For **Windows 10 Home Edition** you will need to install the legacy [Docker Toolbox](https://docs.docker.com/toolbox/toolbox_install_windows/). The instructions can be found [here](https://docs.docker.com/toolbox/toolbox_install_windows/) and are indepth so should be followed carefully

   * For **Mac** or **any other Windows Edition** Visit https://hub.docker.com/?overlay=onboarding
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
   docker pull gradle:jdk11
   ```

## Baseline project for the labs
In order to save time during the workshop it would useful for you to download the baseline SpringBoot project and cache some of dependencies that will be needed for the labs.

### Download SpringBoot project
The following URL is pre-configured to take you directly to https://start.spring.io and download a zip file containing the project. Just open your browser and enter the URL, the download should begin.

`https://start.spring.io/starter.zip?type=gradle-project&language=java&bootVersion=2.1.9.RELEASE&baseDir=apiworkshop&groupId=com.jpgough&artifactId=apiworkshop&name=apiworkshop&description=starter%20project%20for%20api%20workshop&packageName=com.jpgough.apiworkshop&packaging=jar&javaVersion=11&dependencies=web&dependencies=cloud-contract-verifier`

Unzip the downloaded archive to a location on your file system.

### Import project into you IDE
Import the project into IntelliJ by opening the the `build.gradle` file as a project.

![Intellij Project Import](../01-spring-boot/01B-sample-import.png)

On hitting OK the project will most likely download the internet (or at least all the required dependencies). 
Once this has completed your baseline project is ready. 
You can try running the tests to verify that your project builds and the context loads correctly.

#### Eclipse

File `->` Import `->` Gradle `->` Existing Gradle Project.    
Follow the wizard to bring in the project and resolve the dependencies.
 

![Eclipse Project Import](../01-spring-boot/01B2-eclipse.png)


# Recommended prerequisite.
We recommend installing the following software ahead of the workshop, though they are small so it is not essential

## Swagger diff

   * Download the following jar file: `wget http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/2.3.1/swagger-codegen-cli-2.3.1.jar -O swagger-codegen-cli.jar`
   * Make a note of where you have installed it as it will be used in the workshop

## A REST client
We will be making requests to the services we build so it is really useful to have a rest client. Please use any of the suggested options:
   * In IntelliJ there is one available in Tools -> HTTP Client -> Test RESTful Web Service
   * For Windows or Mac [POSTman](https://www.getpostman.com/downloads/)
   * For chrome [restlet client](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en)
