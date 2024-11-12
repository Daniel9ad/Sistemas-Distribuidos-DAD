import pika

# Configuración de conexión a RabbitMQ
rabbitmq_host = 'localhost'  # Cambia esto a la IP o el hostname de tu servidor RabbitMQ
queue_name = 'user_notifications'  # Cambia este nombre al de la cola que quieres escuchar

def callback(ch, method, properties, body):
    print(" [x] Recibido %r" % body)

# Establece la conexión y el canal
connection = pika.BlockingConnection(pika.ConnectionParameters(host=rabbitmq_host))
channel = connection.channel()

# Declara la cola. `durable=True` asegura que la cola persista si RabbitMQ se reinicia.
channel.queue_declare(queue=queue_name, durable=False)

print(f" [*] Esperando mensajes en la cola '{queue_name}'. Para salir presiona CTRL+C")

# Escucha mensajes de la cola y llama a la función `callback` cuando llega un mensaje
channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)

# Mantiene el script en espera de mensajes
channel.start_consuming()