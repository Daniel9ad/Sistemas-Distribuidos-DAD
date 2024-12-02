<?php

use App\Http\Controllers\FacturaController;
use App\Http\Controllers\TituloController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:sanctum');

Route::apiResource('titulo', TituloController::class);
Route::apiResource('factura', FacturaController::class);