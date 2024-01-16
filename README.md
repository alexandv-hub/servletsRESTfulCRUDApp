Практика.

Описание задачи:

Необходимо реализовать REST API, которое взаимодействует с файловым хранилищем и предоставляет возможность получать доступ к файлам и истории загрузок.

Сущности:
- User -> Integer id, String name, List<Event> events
- Event -> Integer id, User user, File file
- File -> Integer id, String name, String filePath

Требования:
- Все CRUD операции для каждой из сущностей
- Придерживаться подхода MVC
- Для сборки проекта использовать Maven
- Для взаимодействия с БД - Hibernate
- Для конфигурирования Hibernate - аннотации
- Инициализация БД должна быть реализована с помощью flyway
- Взаимодействие с пользователем необходимо реализовать с помощью Postman (https://www.getpostman.com/)

Технологии: 
- Java, MySQL, Hibernate, HTTP, Servlets (JAX-RS, Jersey), Maven, Flyway, Swagger (OpenApi).

### Инструкции по запуску
Для запуска приложения следуйте этим шагам:

1. Клонируйте репозиторий на ваш локальный компьютер.
2. Установите и настройте MySQL Server.
3. Откройте терминал и перейдите в директорию проекта.
4. Выполните команду mvn clean install или mvn package.
5. Установите и настройте Tomcat Server.
6. Разместите собранный .war файл в директорию webapps вашего Tomcat сервера.
7. Запустить Tomcat с помощью командной строки можно следующим образом:
- Windows:
   Откройте командную строку (CMD).
   Перейдите в директорию bin вашего установленного Tomcat. Например: cd C:\Program Files\Apache Tomcat\bin.
   Запустите startup.bat командой startup.

- Linux/MacOS:
   Откройте терминал.
   Перейдите в директорию bin вашего установленного Tomcat. Например: cd /opt/tomcat/bin.
   Запустите startup.sh командой ./startup.sh.
8. Приложение теперь должно быть доступно по соответствующим URL.
9. OPEN API спецификация доступна по адресу http://localhost:8080/servletsRESTfulCRUDApp_war_exploded/api/openapi.json .
10. Swagger документация к REST API доступна по адресу http://localhost:8080/servletsRESTfulCRUDApp_war_exploded/swagger-ui/ .


### Технические требования
- Java версии 11 или выше.
- Наличие JRE (Java Runtime Environment) на компьютере.
- Tomcat Server версии 9.0.x или выше.

### Разработчик
(VA)

---
