const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('db_enrollment_service', 'root', '', {
  host: 'localhost',
  dialect: 'mysql'
});


const Matricula = sequelize.define('Matricula', {
  id: {
    type: Sequelize.BIGINT,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true,
    unsigned: true // Aquí especificamos que es UNSIGNED
  },
  usuario_id: {
    type: Sequelize.BIGINT,
    allowNull: false,
    unsigned: true // Agregamos unsigned: true para el BIGINT UNSIGNED
  },
  curso_id: {
    type: Sequelize.BIGINT,
    allowNull: false,
    unsigned: true // Agregamos unsigned: true para el BIGINT UNSIGNED
  }
  
}, {
  tableName: 'matriculas',
  timestamps: true, // Para manejar los campos created_at y updated_at automáticamente
  createdAt: 'created_at',
  updatedAt: 'updated_at'
});

module.exports = { sequelize, Matricula };

// Realiza las migraciones
sequelize.sync().then(() => {
  console.log("Database & tables created!");
});