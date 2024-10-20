<?php

namespace App\Http\Controllers;

use App\Models\Estudiante;
use Illuminate\Http\Request;

class EstudianteController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return Estudiante::all();
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $estudiante = Estudiante::create($request->all());
        return response()->json($estudiante, 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        $estudiante = Estudiante::find($id);
        return response()->json($estudiante, 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $estudiante = Estudiante::find($id);
        $input = $request->all();
        $estudiante->update($input);
        return response()->json($estudiante, 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $estudiante = Estudiante::find($id);
        $estudiante->delete();
        return response()->json($estudiante, 200);
    }
}
