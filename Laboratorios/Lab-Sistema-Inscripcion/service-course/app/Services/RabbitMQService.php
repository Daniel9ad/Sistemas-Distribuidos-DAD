<?php

namespace App\Services;

use PhpAmqpLib\Message\AMQPMessage;
use PhpAmqpLib\Connection\AMQPStreamConnection;

class RabbitMQService
{
    protected $connection;
    protected $channel;

    public function __construct()
    {
        // Configuración de conexión
        $this->connection = new AMQPStreamConnection(
            env('RABBITMQ_HOST', 'localhost'),
            env('RABBITMQ_PORT', 5672),
            env('RABBITMQ_USER', 'guest'),
            env('RABBITMQ_PASSWORD', 'guest'),
            env('RABBITMQ_VHOST', '/')
        );

        // Crear un canal de comunicación con RabbitMQ
        $this->channel = $this->connection->channel();
    }

    /**
     * Publicar un mensaje en un canal de RabbitMQ.
     *
     * @param string $message
     * @param string $queue
     * @return void
     */
    public function publishMessage(string $message, string $queue = 'default')
    {
        // Declarar la cola en RabbitMQ
        $this->channel->queue_declare($queue, false, true, false, false);

        // Crear el mensaje
        $msg = new AMQPMessage(
            $message,
            ['delivery_mode' => AMQPMessage::DELIVERY_MODE_NON_PERSISTENT] // Hacer que el mensaje persista
        );

        // Publicar el mensaje en la cola especificada
        $this->channel->basic_publish($msg, '', $queue);

        // Mostrar mensaje en la consola (para depuración)
        echo " [x] Sent '$message'\n";

        // Cerrar el canal y la conexión
        $this->channel->close();
        $this->connection->close();
    }
}
