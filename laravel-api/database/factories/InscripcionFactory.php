<?php

namespace Database\Factories;

use App\Models\Curso;
use App\Models\Estudiante;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Inscripcion>
 */
class InscripcionFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        $estudiante = Estudiante::inRandomOrder()->first();
        $curso = Curso::inRandomOrder()->first();
        return [
            'Fecha'=>fake()->date(),
            'estudiante_id'=>$estudiante->id,
            'curso_id'=>$curso->id,
        ];
    }
}
