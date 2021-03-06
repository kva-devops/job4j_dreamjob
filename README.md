# Приложение Dream Job
[![Build Status](https://app.travis-ci.com/kva-devops/job4j_dreamjob.svg?branch=master)](https://app.travis-ci.com/kva-devops/job4j_dreamjob)

## О проекте
#### Описание
Web-Приложение для поиска работы. Позволяет размещать вакансии и резюме соискателей.
Предусмотрена регистрация пользователей. Кандидаты при размещении резюме могут загружать свои фотографии.
В качестве хранилища используется база данных Postgresql, отображение страниц - JSP, Servlet, HTML шаблоны Bootstrap.
В JSP используется библиотека тегов JSTL.
 
#### Технологии
>JDK14, Maven, Servlet, PostgreSQL, JSTL, JS, AJAX, Mockito
## Сборка
0. Скачайте исходники 
1. Создайте базу данных в соответствии с настройками, указанными в файле *db.properties*. 
   Файл находится в корневой директории.
2. Выполните сборку проекта: `mvn install`
3. Скопируйте полученный файл *target/mvn-webapp-test.war* на свой сервер

## Как пользоваться
Чтобы начать работать с приложением необходимо авторизоваться или зарегистрироваться:

![Login](images/1.png)

![Register](images/2.png)

После авторизации пользователь попадает на главную страницу, на которой располагается подборка свежих вакансий:

![Posts](images/3.png)

Навигация по приложению реализована через верхнее меню.
Посмотреть кандидатов, а также добавить резюме или разместить вакансию можно через соответствующий пункт меню:

![Candidates](images/4.png)

![AddPosts](images/5.png)

![AddCandidates](images/6.png)

## Контакты
Кутявин Владимир

skype: tribuna87

email: tribuna87@mail.ru

telegram: @kutiavinvladimir


