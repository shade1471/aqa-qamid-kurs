# Порядок действий для запуска автотестов:

**1.Приложение для заказа тура использует подключение к СУБД. Для этого можно поднять образ СУБД локально на ПК, где будет производиться запуск тестов, используя инструмент Docker.**

Для этого открыть терминал в каталоге проекта и набрать команду ```DOCKER-COMPOSE UP```.

**2. До запуска авто-тестов требуется запустить само приложение, расположенное в папке artifacts родительского каталога.**

Для запуска открыть терминал в каталоге artifacts и набрать команду ```java -jar aqa-shop.jar```
