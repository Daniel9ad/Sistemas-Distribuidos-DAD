const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('db_entel', 'root', '', {
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
  }
}, {
  tableName: 'personas',
  timestamps: true,
  createdAt: 'created_at',
  updatedAt: 'updated_at'
});

const Factura = sequelize.define('Factura', {
  id: {
    type: Sequelize.STRING(9),
    allowNull: false,
    primaryKey: true,
    unsigned: true,
  },
  personaid: {
    type: Sequelize.STRING(50),
    allowNull: false
  },
  monto: {
    type: Sequelize.STRING(50),
    allowNull: false
  },
  estado: {
    type: Sequelize.STRING(50),
    allowNull: false
  }
}, {
  tableName: 'facturas',
  timestamps: true,
  createdAt: 'created_at',
  updatedAt: 'updated_at'
});

module.exports = { sequelize, Persona, Factura };

// Realiza las migraciones
sequelize.sync().then(() => {
  console.log("Database & tables created!");
});