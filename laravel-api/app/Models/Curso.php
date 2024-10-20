<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Curso extends Model
{
    /** @use HasFactory<\Database\Factories\CursoFactory> */
    use HasFactory;

    protected $fillable = [
        'nombre', 
        'costo'
    ];
    
    public function inscripciones() {
        return $this->hasMany(Inscripcion::class);
    }
}