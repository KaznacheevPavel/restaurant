# Internship restaurant

Проект на базе Spring Boot и микросервисной архитектуры для ресторана.

### Структура проекта:
* ```common-lib``` - общая библиотека для обоих сервисов.
* ```waiter-service``` - сервис официантов.
* ```kitchen-service``` - сервис кухни.

### Используемые технологии:
* Spring Boot
* Spring Actuator
* Spring Data JPA
* Spring Validation
* Hibernate
* MyBatis
* PostgreSQL
* Liquibase
* Feign
* Kafka
* Swagger

### Запуск проекта
1. Клонируйте репозиторий:  
```git clone https://gitlab.com/KaznacheevPavel/internship-restaurant.git```
2. Запустите БД и kafka через docker-compose:  
```docker-compose up```
3. Соберите проект:  
```mvn clean install```
4. Запустите сервисы:
```
cd waiter-service
mvn spring-boot:run
```
```
cd kitchen-service
mvn spring-boot:run
```

### Swagger
Сервисы имеют свою swagger документацию, доступную по ссылкам:
* [kitchen-service-swagger-ui](http://localhost:8081/api/v1/docs/swagger-ui.html)
* [waiter-service-swagger-ui](http://localhost:8082/api/v1/docs/swagger-ui.html)

Также вы можете воспользоваться Postman коллекциями, которые экспортированы в папках с ресурсами каждого сервиса.

### Возможности:
#### ```waiter-service```
* Добавление нового официанта.
* Добавление нового блюда и отправка его в сервис кухни через ```Feign```.
* Добавление нового заказа и отправка его в сервис кухни через ```Feign```.
* Добавление нового платежа для оплаты заказа.
* Принятие уведомлений через ```Kafka``` от сервиса кухни со статусом заказа.

#### ```kitchen-service```
* Принятие нового блюда от сервиса официантов.
* Добавление готовых порций для блюда.
* Принятие нового заказа от сервиса официантов.
* Завершение приготовления заказа и отправка уведомления через ```Kafka```.
* Отмена заказа и отправка уведомления через ```Kafka```.
