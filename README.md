# Maha Payment Backend API
SpringBoot Back end Application Uses Java 15, Maven Consumes Json request body and calculate price for checkout process


## Installation-Prerequisites

* Download and install [Java 15](https://www.oracle.com/java/technologies/javase-downloads.html), also make sure to setup [JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux) environment variable.
* Download and install [Maven](https://maven.apache.org/install.html), make sure to setup [ MAVEN_HOME](https://mkyong.com/maven/how-to-install-maven-in-windows/) for windows OS.
* Download and install [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) code version control tool.
* Download And Setup [MySQL](https://dev.mysql.com/downloads/installer/) service on your local host
* Download And Install [MySQL workbench](https://dev.mysql.com/downloads/workbench/) on your machine
* Download And Install [Postman](https://www.postman.com/downloads/) on your machine, or any Rest Client you are familiar with.
* Download And Install [Mongo DB](https://www.mongodb.com/try/download/community) on your machine
* Application Used the embed tomcat 9 - Spring Boot - No need for Tomcat Server.
* Import the source code as maven project into IDE ([Intellij](https://www.jetbrains.com/idea/) , [Eclips STS](https://spring.io/tools)) workspace


## Installation-Steps

* Clone source code into your machine :
```bash
git clone https://github.com/qaisalazzeh/MahaPaymentBackend.git
```

Once build is completed you can now import the project over IDE 
* Make Sure MySql Server Is working on your machine with below information - you can change them , please make sure to update application.properties if needed:
(jdbc:mysql://localhost:3306/mahaBackend, username=root, password=root)

* Now open MySql Workbench and lets create the table: 
```sql
CREATE TABLE `merchant_entity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `ACCESS_CODE` varchar(45) DEFAULT NULL,
  `MAHA_CODE` varchar(45) DEFAULT NULL,
  `PHONE_NUMBER` varchar(45) DEFAULT NULL,
  `WEBSITE` varchar(45) DEFAULT NULL,
  `SHA_PASS` varchar(45) NOT NULL,
  `IDENTIFIER` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDENTIFIER_UNIQUE` (`IDENTIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```
and now lets add test merchant 

```sql
INSERT INTO `mahaBackend`.`Merchant_entity` 
(`id`, `NAME`, `ACCESS_CODE`, `MAHA_CODE`, `PHONE_NUMBER`, `WEBSITE`, `SHA_PASS`, `IDENTIFIER`) VALUES ('1', 'Qais Azzeh', 'code293084', 'maha001', '0799793061', 'www.qazzeh.maha.com.ae', 'oiquywe%$#', 'qazzeh');
```

* From Your Terminal , Make Sure you are running Mongo Enginer: 
```bash
sudo systemctl start mongod
```

* Now open MongoDB Compass and import the below catalog collection:

```json
[{
  "_id": "001",
  "name": "Rolex",
  "price": "100",
  "discountThreshold": "3",
  "discountPrice": "200",
  "_class": "com.example.MahaPaymentServices.mongo.entities.WatchCatalog"
},{
  "_id": "002",
  "name": "Michael Kors",
  "price": "80",
  "discountThreshold": "2",
  "discountPrice": "120",
  "_class": "com.example.MahaPaymentServices.mongo.entities.WatchCatalog"
},{
  "_id": "003",
  "name": "Swatch",
  "price": "50",
  "_class": "com.example.MahaPaymentServices.mongo.entities.WatchCatalog",
  "discountThreshold": "0",
  "discountPrice": "0"
},{
  "_id": "004",
  "name": "Casio",
  "price": "30",
  "_class": "com.example.MahaPaymentServices.mongo.entities.WatchCatalog",
  "discountPrice": "0",
  "discountThreshold": "0"
}]

```


* Now Navigate to Project Directory and make sure your are able to see pom.xml file then tap :
```bash
mvn clean install
```

* Now You can Run the Back end Service from Terminal or Run As Spring boot application from your IDE
```bash
mvn spring-boot:run
```

## Sample of Request

* Url : http://localhost:8090/checkout
* Headers: {Content-Type: application/json}
* Method : POST
* Request Body :

```json
{
	"mahaCode": "maha001",
	"accessCode": "code293084",
	"signature": "bd92e5bc96087ac908af22f9650b670e35a55a69",
	"identifier": "qazzeh",
	"itemsBasket": [
		"001",
		"001",
		"002",
		"001",
		"002",
		"001",
		"001",
		"002",
		"001",
		"004",
		"004",
		"004",
		"003",
		"003",
		"003",
		"003",
		"001",
		"005"
	]
}
```
* Response Body :
```json
{
  "responseCode": "00000",
  "responseMessage": "Request Processed Successfully",
  "price": 990,
  "invoiceResponseMap": {
    "001": {
      "itemName": "Rolex",
      "count": 7,
      "totalItemPrice": 500
    },
    "002": {
      "itemName": "Michael Kors",
      "count": 3,
      "totalItemPrice": 200
    },
    "003": {
      "itemName": "Swatch",
      "count": 4,
      "totalItemPrice": 200
    },
    "004": {
      "itemName": "Casio",
      "count": 3,
      "totalItemPrice": 90
    },
    "005": {
      "itemName": null,
      "count": 1,
      "totalItemPrice": null
    }
  }
}

```
### You Can also Use Curl Utility for to post your request 
```bash
curl -XPOST-H "Content-Type: application/json" http://localhost:8090/.......
curl  -H  http://localhost:8090/.......
```

##### Author : Qais Azzeh, Software Engineer, [Email me](qais.azzeh@gmail.com)


