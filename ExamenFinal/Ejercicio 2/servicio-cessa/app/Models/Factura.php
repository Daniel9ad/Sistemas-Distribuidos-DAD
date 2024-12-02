<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Factura extends Model
{
    /** @use HasFactory<\Database\Factories\FacturaFactory> */
    use HasFactory;
    protected $fillable = [
        'persona_id',
        'monto',
        'fecha_emision',
        'estado',
    ];
    public function persona()
    {
        return $this->belongsTo(Persona::class, 'persona_id', 'id');
    }
}
