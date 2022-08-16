**Github Actions:**  
[![AQA-qamid-kurs](https://github.com/shade1471/aqa-qamid-kurs/actions/workflows/gradle.yml/badge.svg)](https://github.com/shade1471/aqa-qamid-kurs/actions/workflows/gradle.yml)

[Описание задания](https://github.com/shade1471/aqa-qamid-kurs/blob/master/Work's%20Description.md)

# Порядок действий для запуска автотестов:

**1. Приложение для заказа тура использует подключение к СУБД для сохранения данных по операциям. Для этого нужно развернуть образ СУБД локально на ПК, где будет производиться запуск тестов, используя инструмент Docker.**

Для этого открыть терминал в каталоге проекта и набрать команду:

Для работы через СУБД MySQL :```DOCKER-COMPOSE UP -d```.

Для работы через СУБД Postgres: ```docker-compose -f .\docker-compose-postgres.yml up -d```.

**2. До запуска авто-тестов требуется запустить само приложение, расположенное в папке artifacts родительского каталога.**

Для запуска открыть терминал в каталоге artifacts и набрать команду 

При работе с СУБД MySql: ```java -jar aqa-shop.jar```

При работе с СУБД Postgres: ```java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar aqa-shop.jar```

**3. Для запуска авто-тестов можно воспользоваться командой в терминале (находясь в каталоге проекта) ```./gradlew test``` либо ```./gradlew test -Dselenide.headless=true``` для запуска в безоконном режиме. Если проект открыт в среде разработки IntelliJ Idea запустить тесты можно через ```Run``` (двойное нажатие Ctrl) и дальнейший ввод команды ```gradlew test``` или ```gradlew test -Dselenide.headless=true``` для безоконного режима**

Результаты прогона тестов сформированные стандартными средствами gradle доступны будут в каталоге проекта ```/build/reports/tests/test/index.html```

Так же в проекте подключен фреймворк Allure для генерации более наглядных отчетов после прохождения тестов:

Сформировать отчеты средствами Allure после прохождения тестов, можно воспользовавшись командой в терминале ```./gradlew allureServe``` для последующего открытия страницы с отчетами в браузере сразу после их формирования.
