<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\CursoController;
use App\Http\Controllers\EstudianteController;
use App\Http\Controllers\InscripcionController;
use App\Http\Middleware\JwtMiddleware;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:sanctum');

Route::post('login', [AuthController::class, 'login']);

Route::apiResource('estudiante', EstudianteController::class)->middleware(JwtMiddleware::class);
Route::apiResource('curso', CursoController::class)->middleware(JwtMiddleware::class);
Route::apiResource('isncripcion', InscripcionController::class)->middleware(JwtMiddleware::class);


