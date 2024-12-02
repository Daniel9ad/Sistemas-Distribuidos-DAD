const express = require('express');
const mysql = require('mysql2/promise');
const app = express();

app.use(express.json());

const db = mysql.createPool({
  host: 'localhost',
  user: 'root',
  password: '', 
  database: 'db_elapas',
});

app.get('/api/factura/:ci', async (req, res) => {
  const { ci } = req.params;
  try {
    const [persona] = await db.query('SELECT * FROM personas WHERE ci = ?', [ci]);
    if (persona.length === 0) {
      return res.status(404).json({ error: 'Persona no encontrada.' });
    }
    const personaId = persona[0].id;
    const [facturas] = await db.query('SELECT * FROM facturas WHERE persona_id = ?', [personaId]);
    if (facturas.length === 0) {
      return res.status(404).json({ error: 'No hay facturas asociadas a esta persona.' });
    }
    res.status(200).json({
      persona: persona[0],
      facturas: facturas,
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al obtener las facturas.' });
  }
});

app.patch('/api/factura/:id', async (req, res) => {
  const { id } = req.params;

  try {
    const [factura] = await db.query('SELECT * FROM facturas WHERE id = ?', [id]);

    if (factura.length === 0) {
      return res.status(404).json({ error: 'Factura no encontrada.' });
    }

    await db.query('UPDATE facturas SET estado = ? WHERE id = ?', ['pagado', id]);

    const [facturaActualizada] = await db.query('SELECT * FROM facturas WHERE id = ?', [id]);

    res.status(200).json({
      message: 'El estado de la factura se ha actualizado a pagado.',
      factura: facturaActualizada[0],
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Error al actualizar la factura.' });
  }
});

const PORT = 3000;

app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
