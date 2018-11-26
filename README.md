# Тестовое задание (REST сервер)

# Примеры запросов:

Создать пользователя:
```HTTP
POST /users?name=John&age=25&height=1.75&sex=false&favcolor=Red&favcolor=Green&favcolor=Blue
```

Удалить пользователя с ID 1:
```HTTP
DELETE /users/1
```

Отобразить пользователя с ID 1:
```HTTP
GET /users/1
```

Отобразить всех пользователей:

```HTTP
GET /users/
```

# Представление ресурсов

Сервер может представлять ресурсы в формате JSON и XML. Запрашеваемый тип представления передается серверу в HTTP заголовке Accept, например:

Представление в JSON:  
```HTTP
GET /users/1
Accept: application/json
```

Представление в XML:  
```HTTP
GET /users/1
Accept: application/xml
```
