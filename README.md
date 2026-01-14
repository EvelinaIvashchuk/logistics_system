# Logistics System

Цей проект складається з мікросервісів для демонстрації логістичного процесу з інтегрованою безпекою OIDC.

## Мікросервіси та вхідні точки

1. **Gateway Service (Вхідна точка)** (Порт 9000)
   - Надає графічний інтерфейс (UI) для доступу до системи.
   - Виконує роль OAuth2 Client для аутентифікації через Keycloak.
   - Маршрутизує запити до мікросервісів:
     - Order API: `/order-api/**`
     - Inventory API: `/inventory-api/**`
     - Shipping API: `/shipping-api/**`
   - Адреса: http://localhost:9000

2. **Keycloak (Security)** (Порт 8090)
   - OIDC Identity Provider.
   - Realm: `logistics-realm`
   - Користувачі:
     - `admin` / `admin`
     - `user` / `user`
   - Адреса: http://localhost:8090

3. **Order Service** (Порт 8080)
   - Приймає REST запити на створення замовлень (потрібен JWT токен).
   - Комунікує з Inventory та Shipping сервісами через gRPC.

4. **Inventory Service** (Порт 8081 / gRPC 9091)
   - Управління запасами товарів (потрібен JWT токен для REST).

5. **Shipping Service** (Порт 8082 / gRPC 9092)
   - Оформлення доставки (потрібен JWT токен для REST).

6. **Admin Server** (Порт 8888)
   - Spring Boot Admin для моніторингу сервісів.
   - Адреса: http://localhost:8888

7. **RabbitMQ** (Порт 15672)
   - Брокер повідомлень для асинхронного обміну даними.
   - Management UI: http://localhost:15672 (guest/guest)

### Запуск через Docker (рекомендовано)

Для запуску всіх сервісів однією командою використовуйте Docker Compose. Завдяки багатоетапній збірці (multi-stage builds), Docker сам збере JAR-файли всередині контейнерів.

1. Запустіть контейнери:
   ```bash
   docker-compose up -d --build
   ```

2. Сервіси будуть доступні за наступними адресами:
   - **Gateway (UI)**: http://localhost:9000
   - **Admin Server (Dev UI)**: http://localhost:8888
   - **Keycloak**: http://localhost:8090
   - **RabbitMQ Management**: http://localhost:15672 (guest/guest)

3. Для примусового перезбирання при зміні коду використовуйте ту саму команду `docker-compose up -d --build`.

## Як запустити локально (без Docker)

Запустіть кожен сервіс окремо з кореневої директорії проекту:

**Windows (PowerShell/CMD):**
```powershell
.\gradlew :inventory-service:bootRun
.\gradlew :shipping-service:bootRun
.\gradlew :order-service:bootRun
.\gradlew :admin-server:bootRun
```

**Linux/macOS:**
```bash
./gradlew :inventory-service:bootRun
./gradlew :shipping-service:bootRun
./gradlew :order-service:bootRun
./gradlew :admin-server:bootRun
```

Якщо виникають помилки з пошуком головного класу, спробуйте спочатку очистити проект:
```bash
.\gradlew clean
```

## Як протестувати

Використовуйте `curl` для створення замовлення:

```bash
curl -X POST "http://localhost:8080/orders?productId=product-1&quantity=2&address=Kyiv,Ukraine"
```

### Приклади товарів у системі:
- `product-1` (доступно 10 шт)
- `product-2` (доступно 5 шт)
- `product-3` (немає в наявності)
