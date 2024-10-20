<?php

namespace App\Http\Controllers;

use App\Models\Inscripcion;
use Illuminate\Http\Request;

class InscripcionController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return Inscripcion::all();
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $inscripcion = Inscripcion::create($request->all());
        return response()->json($inscripcion, 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        $inscripcion = Inscripcion::find($id);
        return response()->json($inscripcion, 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $inscripcion = Inscripcion::find($id);
        $input = $request->all();
        $inscripcion->update($input);
        return response()->json($inscripcion, 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $inscripcion = Inscripcion::find($id);
        $inscripcion->delete();
        return response()->json($inscripcion, 200);
    }
}
