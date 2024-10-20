<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Estudiante>
 */
class EstudianteFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'dni'=>fake()->randomNumber(9),
            'nombres'=>fake()->name(),
            'apellidos'=>fake()->lastname(),
            'celular'=>fake()->phoneNumber(),
            'email'=>fake()->unique()->safeEmail(),
        ];
    }
}
