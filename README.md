
# Rent ACorDapp Template - Kotlin

A simple DLT app to share rent agreements in a decentalizes, distributed and private way.


# Pre-Requisites

Java 8

# Usage


## Running the nodes

 - Navigate to project repo
 - Run deploynodes task  
	 - Linux - ./gradlew clean deploynodes
	 - Windows- gradlew.bat  clean deployNodes
 - Run the nodes 
     - Linux - ./build/nodes/runnodes
     - Windows - call  workflows-kotlin\build\nodes\runnodes.bat

 



## Running the webserver

##### Via the command line
 - Navigate to repo/clients .
 - Run deploynodes task  
	 - gradle runOwner1
	 - gradle runParty1
