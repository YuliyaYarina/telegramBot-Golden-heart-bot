# Проект Bot-помощник приюта "Золотое Сердце"

Bot-помощник - это бот для Telegram, который отвечает на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта.

Также в течение месяца просит новых хозяев присылать ежедневный отчет о том, как животное приспосабливается к новой обстановке.

Проектная работа курса «Java‑разработчик» платформы SkyPro.

### Стек технологий
* Java
*
* 

## Установка 
1. Клонируйте репозиторий с github
2. Создайте виртуальное окружение
3. Установите зависимости в ‘pom.xml’
4. Создайте файл resources
5. Впишите в resources переменные:

spring.application.name=golden-heart-bot

spring.datasource.url= "Адрес прокси"
spring.datasource.username= "Логин на прокси"
spring.datasource.password= "Пароль на прокси"
telegram.bot.token= "API-ключ бота"

spring.liquibase.change-log=classpath:liquibase/changelog-master.yml

6. Запустите бота 
