# Spring WebSocket Chat - skeleton

快速启动：
1. 构建并启动（需要 Docker & docker-compose）:
   docker-compose up --build

2. 打开前端:
   http://localhost:8081

使用说明：
- 输入用户名点击 Login，会调用 /api/login 获得 JWT 并在 CONNECT 时带上 Authorization。
- 默认 conversationId 为 room1，向该 topic 发送/接收消息。
- 后端监听 /ws STOMP endpoint，消息通过 /app/chat.send 发送，广播到 /topic/conversation.{id}。

注意（生产须改进）：
- 更安全的 JWT 秘钥管理
- 完善异常处理与输入校验
- 使用 Broker Relay（RabbitMQ）或增强 Redis 序列化策略
- rate limiting、auth/permission、attachment 存储、消息分页等
