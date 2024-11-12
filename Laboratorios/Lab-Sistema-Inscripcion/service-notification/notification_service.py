import pika
import json
import os
from flask import Flask, jsonify
from dotenv import load_dotenv
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import smtplib
import logging

# Configuración de logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    filename='notification_service.log'
)
logger = logging.getLogger(__name__)

# Cargar variables de entorno
load_dotenv()

app = Flask(__name__)

# Configuración de correo
SMTP_SERVER = os.getenv('SMTP_SERVER', 'smtp.gmail.com')
SMTP_PORT = int(os.getenv('SMTP_PORT', '587'))
SMTP_USERNAME = os.getenv('SMTP_USERNAME')
SMTP_PASSWORD = os.getenv('SMTP_PASSWORD')

# Configuración de RabbitMQ
RABBITMQ_HOST = os.getenv('RABBITMQ_HOST', 'localhost')
RABBITMQ_USER = os.getenv('RABBITMQ_USER', 'guest')
RABBITMQ_PASS = os.getenv('RABBITMQ_PASS', 'guest')

class EmailService:
    @staticmethod
    def send_email(to_email, subject, body):
        try:
            msg = MIMEMultipart()
            msg['From'] = SMTP_USERNAME
            msg['To'] = to_email
            msg['Subject'] = subject
            
            msg.attach(MIMEText(body, 'plain'))
            
            server = smtplib.SMTP(SMTP_SERVER, SMTP_PORT)
            server.starttls()
            server.login(SMTP_USERNAME, SMTP_PASSWORD)
            
            server.send_message(msg)
            server.quit()
            
            logger.info(f"Email enviado exitosamente a {to_email}")
            return True
        except Exception as e:
            logger.error(f"Error al enviar email: {str(e)}")
            return False

class NotificationConsumer:
    def __init__(self):
        self.connection = None
        self.channel = None
        self.email_service = EmailService()
        self.connect()

    def connect(self):
        try:
            # Establecer conexión con RabbitMQ
            # credentials = pika.PlainCredentials(RABBITMQ_USER, RABBITMQ_PASS)
            # parameters = pika.ConnectionParameters(
            #     host=RABBITMQ_HOST,
            #     credentials=credentials
            # )
            # self.connection = pika.BlockingConnection(parameters)
            self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
            self.channel = self.connection.channel()
            
            # Declarar colas
            self.channel.queue_declare(queue='user_notifications')
            self.channel.queue_declare(queue='course_notifications')
            self.channel.queue_declare(queue='enrollment_notifications')
            
            logger.info("Conexión establecida con RabbitMQ")
        except Exception as e:
            logger.error(f"Error al conectar con RabbitMQ: {str(e)}")
            raise

    def process_message(self, ch, method, properties, body):

        try:
            message = json.loads(body)
            notification_type = message.get('type')
            to_email = message.get('email')
            
            if notification_type == 'user_registration':
                subject = "Bienvenido al Sistema de Cursos"
                body = f"Hola {message.get('name')},\n\nGracias por registrarte en nuestro sistema."
                
            elif notification_type == 'course_enrollment':
                subject = f"Matriculación Exitosa - {message.get('course_name')}"
                body = f"Tu matriculación en el curso {message.get('course_name')} ha sido confirmada."
                
            elif notification_type == 'course_update':
                subject = f"Actualización de Curso - {message.get('course_name')}"
                body = f"El curso {message.get('course_name')} ha sido actualizado."
            
            else:
                logger.warning(f"Tipo de notificación desconocido: {notification_type}")
                return
            
            logger.info("------------------------------")
            logger.info("Enviando")
            logger.info(to_email)
            logger.info(subject)
            logger.info(body)
            # success = self.email_service.send_email(to_email, subject, body)

            # if success:
            #     ch.basic_ack(delivery_tag=method.delivery_tag)
            # else:
            #     # Rechazar el mensaje y reencolar
            #     ch.basic_nack(delivery_tag=method.delivery_tag, requeue=True)
                
        except Exception as e:
            logger.error(f"Error procesando mensaje: {str(e)}")
            ch.basic_nack(delivery_tag=method.delivery_tag, requeue=True)

    def start_consuming(self):
        print("En escucha")
        try:
            # Configurar consumidores para cada cola
            self.channel.basic_consume(
                queue='user_notifications',
                on_message_callback=self.process_message,
                auto_ack=True
            )
            self.channel.basic_consume(
                queue='course_notifications',
                on_message_callback=self.process_message,
                auto_ack=True
            )
            self.channel.basic_consume(
                queue='enrollment_notifications',
                on_message_callback=self.process_message,
                auto_ack=True
            )
            
            logger.info("Iniciando consumo de mensajes...")
            self.channel.start_consuming()
        except KeyboardInterrupt:
            self.channel.stop_consuming()
            self.connection.close()
        except Exception as e:
            logger.error(f"Error en el consumo de mensajes: {str(e)}")
            raise

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({"status": "healthy"}), 200

if __name__ == '__main__':
    # Iniciar el consumidor en un hilo separado
    import threading
    consumer = NotificationConsumer()
    consumer_thread = threading.Thread(target=consumer.start_consuming)
    consumer_thread.start()
    
    # Iniciar la aplicación Flask
    app.run(host='localhost', port=5000)