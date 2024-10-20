<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Estudiante extends Model
{
    /** @use HasFactory<\Database\Factories\EstudianteFactory> */
    use HasFactory;

    protected $fillable = [
        'dni', 
        'nombres', 
        'apellidos', 
        'celular', 
        'email'
    ];
    
    public function inscripciones() {
        return $this->hasMany(Inscripcion::class);
    }
}
