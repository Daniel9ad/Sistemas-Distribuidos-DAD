<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Titulo>
 */
class TituloFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'ci'=>fake()->randomNumber(9),
            'nombre_completo'=>fake()->firstName(),
            'titulo'=>fake()->lastName(),
            'fecha_emicion'=>fake()->date()
        ];
    }
}