LMS Platform - Платформа для онлайн-обучения

### Описание проекта

**LMS (Learning Management System)** — современное веб-приложение для онлайн-обучения на Spring Boot. Позволяет студентам проходить курсы и отслеживать прогресс, а преподавателям — управлять курсами.

## Основные возможности

### Для студентов:
- Регистрация и авторизация
- Просмотр каталога курсов
- Запись на курсы
- Отслеживание прогресса
- Личный кабинет со статистикой

### Для преподавателей:
- Дашборд с аналитикой
- Управление курсами
- Статистика студентов и дохода

## Технологии

### Backend:
- **Java 21**
- **Spring Boot 3.2.0**
- **Spring MVC**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate 6.3.1**
- **Lombok**
- **Liquibase**

### Frontend:
- **Thymeleaf**
- **Bootstrap 5.3.2**
- **Bootstrap Icons**

### База данных:
- **PostgreSQL**
- **Docker**

## Архитектура

com.lms
├── controller/          # MVC контроллеры
├── domain/entity/       # JPA сущности
│   ├── User
│   ├── Course
│   ├── Enrollment
│   ├── Section
│   └── Lesson
├── dto/                 # Data Transfer Objects
├── repository/          # JPA репозитории
├── service/             # Бизнес-логика
└── SecurityConfig       # Конфигурация безопасности
```

##  ER-диаграмма базы данных

```
┌─────────────────┐       ┌─────────────────┐
│     users       │       │     courses     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ username        │       │ title           │
│ email           │       │ description     │
│ password        │       │ created_at      │
│ role            │       │ updated_at      │
│ created_at      │       └─────────────────┘
│ updated_at      │                │
└─────────────────┘                │
        │                          │
        ▼                          ▼
┌─────────────────┐       ┌─────────────────┐
│   enrollments   │       │    sections     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ user_id (FK)    │       │ title           │
│ course_id (FK)  │       │ description     │
│ enrolled_at     │       │ order_index     │
│ progress        │       │ course_id (FK)  │
│ completed       │       └─────────────────┘
└─────────────────┘                │
                                   ▼
                          ┌─────────────────┐
                          │     lessons     │
                          ├─────────────────┤
                          │ id (PK)         │
                          │ title           │
                          │ content         │
                          │ lesson_type     │
                          │ section_id (FK) │
                          └─────────────────┘
```
Инструкция по запуску

 Предварительные требования:
- Java 21+
- Docker Desktop

1. Запуск базы данных

```bash
docker run -d --name lms-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=lms_db -p 5432:5432 postgres:15
```

### 2. Сборка и запуск

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Или через IntelliJ IDEA: запустите `DemoApplication.java`

### 3. Открытие приложения

**http://127.0.0.1:8080**

## API Endpoints

### Публичные:
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/` | Главная страница |
| GET | `/courses` | Список курсов |
| GET | `/courses/{id}` | Детали курса |
| POST | `/register` | Регистрация |
| POST | `/login` | Вход |

### Защищённые:
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/profile` | Личный кабинет |
| GET | `/my-courses` | Мои курсы |
| GET | `/dashboard` | Дашборд преподавателя |
| POST | `/courses/{id}/enroll` | Записаться на курс |

## Безопасность

- Хеширование паролей (BCrypt)
- Ролевая авторизация (STUDENT / INSTRUCTOR)
- Spring Security
- Защита от CSRF

## Оптимизации

- **Liquibase** — миграции БД
- **Индексы** — ускорение запросов
- **Lazy Loading** — отложенная загрузка
- **@Transactional** — транзакции
- **JPA Auditing** — автодаты создания/обновления

## Автор

**Rayana Daurbekova**
