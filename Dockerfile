# Используем официальный образ Java 11
FROM openjdk:17.0.2-jdk-slim-buster

# Устанавливаем рабочую директорию в /app
WORKDIR /app

# Копируем скомпилированный JAR файл внутрь контейнера
COPY build/libs/freelanceFinderServer-0.0.1-SNAPSHOT.jar /app/app.jar

# Опционально: Если вам нужны внешние конфигурационные файлы или ресурсы, вы можете их скопировать.
# COPY src/main/resources/application.properties /app/application.properties

# Опционально: Если ваше приложение использует внешние библиотеки или зависимости, вы можете скопировать их сюда
# COPY lib/ /app/lib/

# Опционально: Если ваше приложение требует какие-либо другие предустановленные зависимости, вы можете установить их
# RUN apt-get update && apt-get install -y <package-name>

# Опционально: Укажите порт, на котором работает ваше Spring Boot приложение
# EXPOSE 8080

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "app.jar"]