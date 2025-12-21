if lsof -i :5672 >/dev/null 2>&1; then
  echo "Port 5672 is already in use. Skipping RabbitMQ start."
else
  echo "Staring rabbitmq"
  docker run -d --rm \
    --name rabbitmq \
    -p 5672:5672 \
    -p 15672:15672 \
    rabbitmq:4-management
fi

mvn spring-boot:run
