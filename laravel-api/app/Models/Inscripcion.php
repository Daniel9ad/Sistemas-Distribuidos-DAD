<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Inscripcion extends Model
{
    /** @use HasFactory<\Database\Factories\InscripcionFactory> */
    use HasFactory;

    protected $fillable = [
        'fecha', 
        'curso_id', 
        'estudiante_id'
    ];
    
    public function curso() {
        return $this->belongsTo(Curso::class);
    }
    
    public function estudiante() {
        return $this->belongsTo(Estudiante::class);
    }
}
