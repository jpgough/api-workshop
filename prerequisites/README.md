# Prerequisites

The workshop requires that you download some applications to be able to complete the tasks on the day.
You should do these tasks before the workshop as some of the files are large.

The tasks that will be the following

* Install Java
* Install one of the following IDE's, Intellij/Eclipse, IntelliJ is recommended
* Download the baseline project for the labs

## Install Java

* Visit https://adoptium.net
* Select the correct version of JDK 17 LTS for your OS (i.e. MacOS, Windows) and download.
* More details on your installation options for various platforms can be found [here](https://adoptium.net/en-GB/installation).

## Install an IDE

We recommend running thw workshop using IntelliJ IDEA.

Download and install here: https://www.jetbrains.com/idea/ 

If not already, please make sure you are using an up-to-date version to help avoid any potential issues.

## Starter project for the labs

In order to save time during the workshop it would useful for you to download the starter SpringBoot project and cache some of dependencies that will be needed for the labs.

### Download SpringBoot project

The following URL is pre-configured to take you directly to https://start.spring.io and set the required dependencies to Spring Web and Spring Contract Verifier. Open your browser and enter the URL, and then click on "Generate" to download a zip file containing the project.

<https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.5&packaging=jar&jvmVersion=17&groupId=com.jpgough&artifactId=apiworkshop&name=apiworkshop&description=Starter%20api%20workshop%20project&packageName=com.jpgough.apiworkshop&dependencies=web,cloud-contract-verifier>

Unzip the downloaded archive to a location on your file system.

### Import project into your IDE

Import the project into IntelliJ by opening the the `pom.xml` file as a project.

![Intellij Project Import](../01-spring-boot/01B-sample-import.png)

On hitting OK the project will most likely download the internet (or at least all the required dependencies).
Once this has completed your baseline project is ready. 
You can try running the tests to verify that your project builds and the context loads correctly.

### Verify it works

Using the Maven Wrapper run `mvnw verify` from the command line to verify that the base project works as expected.

## Optional

The following additional installations allow you to complete all the workshop extension tasks. You do not have to install them to complete the core of this workshop.

### A REST client

We will be making requests to the services we build so it is really useful to have a rest client. Please use any of the suggested options:

* In IntelliJ there is one available in Tools -> HTTP Client -> Test RESTful Web Service
* For Windows or Mac [POSTman](https://www.getpostman.com/downloads/)
* For chrome [restlet client](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=en)

### Docker

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

### Docker images

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

