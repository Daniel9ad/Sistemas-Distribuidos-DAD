// server.js
const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const dotenv = require('dotenv');
const amqp = require('amqplib/callback_api');

dotenv.config();
const app = express();
app.use(express.json());

// Conexión a MongoDB
mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/userservice', {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

// Modelo de Usuario
const userSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    role: { type: String, enum: ['user', 'admin'], default: 'user' },
    createdAt: { type: Date, default: Date.now }
});

const User = mongoose.model('user', userSchema);

// Middleware de autenticación
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        return res.status(401).json({ message: 'Token no proporcionado' });
    }

    jwt.verify(token, process.env.JWT_SECRET || 'tu_clave_secreta', (err, user) => {
        if (err) {
            return res.status(403).json({ message: 'Token inválido' });
        }
        req.user = user;
        next();
    });
};

// Middleware de autorización
// const authorizeAdmin = (req, res, next) => {
//     if (req.user.role !== 'admin') {
//         return res.status(403).json({ message: 'Acceso denegado: se requieren privilegios de administrador' });
//     }
//     next();
// };

// Función para publicar mensajes en RabbitMQ
async function publishMessage(queue, message) {
    try {
        // const connection = await amqp.connect(`amqp://${process.env.RABBITMQ_USER}:${process.env.RABBITMQ_PASS}@${process.env.RABBITMQ_HOST}`);
        // const connection = await amqp.connect('amqp://localhost', function(error0, connection) {})
        // const channel = await connection.createChannel();

        // // Verificar si la cola existe y, en caso contrario, declararla
        // const queueInfo = await channel.checkQueue(queue);
        // if (queueInfo.messageCount === 0 && queueInfo.consumerCount === 0) {
        //     console.log("Entra")
        //     await channel.assertQueue(queue, { durable: true });
        // }

        amqp.connect('amqp://localhost', function (error0, connection) {
            if (error0) {
                console.log("Error=true")
                throw error0;
            }
            connection.createChannel(function (error1, channel) {
                if (error1) {
                    throw error1;
                }

                channel.assertQueue(queue, {
                    durable: false
                });

                channel.sendToQueue(queue, Buffer.from(JSON.stringify(message)));
                console.log(" [x] Sent %s", queue, " -> ", message);
            });
        });
        // channel.sendToQueue(queue, Buffer.from(JSON.stringify(message)));
    } catch (error) {
        console.error('Error al publicar mensaje en RabbitMQ:', error);
    }
}

// Rutas de autenticación
app.post('/auth/register', async (req, res) => {
    try {
        const { username, email, password } = req.body;

        // Verificar si el usuario ya existe
        const existingUser = await User.findOne({ $or: [{ username }, { email }] });
        if (existingUser) {
            return res.status(400).json({ message: 'Usuario o email ya existe' });
        }

        // Encriptar contraseña
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);

        // Crear nuevo usuario
        const user = new User({
            username,
            email,
            password: hashedPassword
        });

        await user.save();

        // Publicar mensaje en RabbitMQ para notificación de registro
        await publishMessage('user_notifications', {
            type: 'user_registration',
            email: user.email,
            name: user.username
        });

        res.status(201).json({ message: 'Usuario registrado exitosamente' });
    } catch (error) {
        res.status(500).json({ message: 'Error en el servidor', error: error.message });
    }
});

app.post('/auth/login', async (req, res) => {
    try {
        const { username, password } = req.body;

        // Buscar usuario
        const user = await User.findOne({ username });
        if (!user) {
            return res.status(400).json({ message: 'Usuario no encontrado' });
        }

        // Verificar contraseña
        const validPassword = await bcrypt.compare(password, user.password);
        if (!validPassword) {
            return res.status(400).json({ message: 'Contraseña incorrecta' });
        }

        // Generar token JWT
        const token = jwt.sign(
            { id: user._id, username: user.username, role: user.role },
            process.env.JWT_SECRET || 'tu_clave_secreta',
            { expiresIn: '24h' }
        );

        res.json({ token });
    } catch (error) {
        res.status(500).json({ message: 'Error en el servidor', error: error.message });
    }
});

// Rutas protegidas
app.get('/users/profile', authenticateToken, async (req, res) => {
    try {
        const user = await User.findById(req.user.id).select('-password');
        res.json(user);
    } catch (error) {
        res.status(500).json({ message: 'Error en el servidor', error: error.message });
    }
});

// app.get('/users', authenticateToken, authorizeAdmin, async (req, res) => {
//     try {
//         const users = await User.find().select('-password');
//         res.json(users);
//     } catch (error) {
//         res.status(500).json({ message: 'Error en el servidor', error: error.message });
//     }
// });

app.put('/users/:id', authenticateToken, async (req, res) => {
    try {
        const { id } = req.params;
        const { username, email } = req.body;

        // Verificar si el usuario es el propietario o admin
        if (req.user.id !== id && req.user.role !== 'admin') {
            return res.status(403).json({ message: 'No autorizado para actualizar este usuario' });
        }

        const updatedUser = await User.findByIdAndUpdate(
            id,
            { username, email },
            { new: true }
        ).select('-password');

        res.json(updatedUser);
    } catch (error) {
        res.status(500).json({ message: 'Error en el servidor', error: error.message });
    }
});

// Iniciar servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Servidor corriendo en puerto ${PORT}`);
});