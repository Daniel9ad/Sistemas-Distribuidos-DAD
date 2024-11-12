const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('db_segip', 'root', '', {
  host: 'localhost',
  dialect: 'mysql'
});


const Persona = sequelize.define('Persona', {
  ci: {
    type: Sequelize.STRING(9),
    allowNull: false,
    primaryKey: true,
    unsigned: true,
  },
  nombres: {
    type: Sequelize.STRING(50),
    allowNull: false
  },
  primer_apellido: {
    type: Sequelize.STRING(30),
    allowNull: true
  },
  segundo_apellido: {
    type: Sequelize.STRING(30),
    allowNull: true
  },
  
}, {
  tableName: 'personas',
  timestamps: true,
  createdAt: 'created_at',
  updatedAt: 'updated_at'
});

module.exports = { sequelize, Persona };

// Realiza las migraciones
sequelize.sync().then(() => {
  console.log("Database & tables created!");
});