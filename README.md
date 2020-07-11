# Smart Food Storage
Nowadays, given the needs due to coronavirus, it is important to know when to replenish stocks, to avoid queuing at the supermarket, save time and safeguard health. That's why smart food storage will help you better manage your inventory. 

## Table of contents
* [General Info](#generalinfo)
* [Technologies](#technologies)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Features](#features)
* [Contact](#contact)

## General Info
An intelligent system that allows us to monitor stocks for each type of product and let us know when one is about to run out, taking consumption into account. A system that uses Raspberry PI, Java, InfluxDB and Grafana to control up to 4 categories of containers: Refrigerator, General Pantry, Freezer and Cellar. The project was developed to be evaluated in the course of industry 4.0

## Technologies
The technologies used for the development of this software are as follows:
- Grafana 7.0.6
- JDK 1.8
- InfluxDB 1.1
 
## Screenshots
![Main Window](./img/main-window.png)
 
## Setup
To run the system you need to start the influx server and connect to it in port 8086. Once you have run the server you should create the db from command line: 
```sh
$ create database smartfoodstoragedb
$ use smartfoodstoragedb
```
Run the .jar file as follows:
```sh
$ java -jar smartfoodstoragedb.jar
```

## Features
#### List of features ready
- Monitors the states of the 4 repositories(Refrigerator, Freezer, Pantry, Cellar)
- If category's status is not good, the LCD will report the cause with a message
- Consumption with grafana graphs
- Product types divided according to barcode
- Measurements for each Category

## Contact
Feel free to contact me at mohamedboutaleb@outlook.it
