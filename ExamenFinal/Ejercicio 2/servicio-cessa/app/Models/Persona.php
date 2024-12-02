<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Persona extends Model
{
    /** @use HasFactory<\Database\Factories\PersonaFactory> */
    use HasFactory;
    
    protected $fillable = [
        'ci',
        'nombre',
        'apellido',
        'email',
    ];

    public function facturas()
    {
        return $this->hasMany(Factura::class, 'persona_id', 'id');
    }
}
