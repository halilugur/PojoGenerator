# Pojo Generator

Pojo Generator is a Java project for database tables.

## Installation

Download or clone project and import Intellij IDE.

*************
if you want run on command line use follow the code.
```sh

java -jar PojoGenerator.jar YOUR_PATH\database.properties

```
********************
## Usage

```sh
Database configuration on pojo.properties file.

database.url    = YOU_DATABASE_URL
database.driver = DRIVER
database.user   = DATABASE_USER_NAME
database.pass   = DATABASE_USER_PASSWORD

model.package.name = PACKAGE_NAME_FOR_GENERATE_CLASS
generated.java.files.path = WILL_BE_CREATE_FOR_GENERATED_CLASS_DESTINATION_PATH

hibernate.is.active=IF YOU GENERATE POJO FOR HIBERNATE PLASE SET TO TRUE
hibernate.fetchType= OBJECT ACTION FOR DATABASE PROCESS
hibernate.cascadeType= GET DATA

```
