# MosErp
Microservices based Open Source ERP System

Most ERP Systems are incredible big monolithic pieces of software.
For most small companies this is just overkill. If you need for example only a product management application or an inventory management, you have to use it all.

## Domains
This projects aims at creating small microservices for each area. There are:

### Environment
Mainly administrative domains like value lists or users.

### Facility
Facilities as used for e.g. inventories.

### Product
A product management application. A product can have multiple attributes, consisting of a plain value or of a enumeration from which to choose.
A concrete product is called a ProductInstance, it has concrete values for its attributes.

### Inventory
A facility can act as a warehouse for ProductInstances. An InventoryItem has mainly a location (Facility), a ProductInstance and a quantity. You can book incoming items through IncomingDelivery and outgoing items through OutgoingDelivery. Between Inventories a InventoryTransfer can be made.

*Dependencies:*
  - Facility
  - Product


## Technology

### Spring Framework
The spring framework is used in almost every place. All microservices are spring boot applications.

### Data Store
The MosErp uses MongoDB for persistence. But since we use the spring repositories as abstraction layer, it should be fairly simply to use it with JPA / SQL as well.

### Interfaces
Every microservice / module has its own REST API. We use Spring Data Rest with Hateoas. This means that the resulting JSON is of HAL format.

Additionally there is a relative url "/structure" where each module posts its internal structure and services. This can be used by a smart frontend to construct a GUI from it. It was mainly inspired from the ALPS structures, but than extended to give much more information which can be important for GUI building.
A Dart based GUI is under development and will be released as OS as well.

### Service Registry
Eureka is used a service registry. This is currently under heavy development.
Goal is to have a quite flexible configuration / registry combination like it is presented at https://dzone.com/articles/learning-spring-cloud.

### Deployment
Docker support is on the way.
Additionally you need the following docker containers running:
  - springcloud/eureka
  - springcloud/configserver


