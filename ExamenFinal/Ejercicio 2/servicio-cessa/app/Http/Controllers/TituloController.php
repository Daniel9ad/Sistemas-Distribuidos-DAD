<?php

namespace App\Http\Controllers;

use App\Models\Titulo;
use Illuminate\Http\Request;

class TituloController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return Titulo::all();
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $titulo = Titulo::create($request->all());
        return response()->json($titulo, 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        $titulo = Titulo::find($id);
        return response()->json($titulo, 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $titulo = Titulo::find($id);
        $input = $request->all();
        $titulo->update($input);
        return response()->json($titulo, 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $titulo = Titulo::find($id);
        $titulo->delete();
        return response()->json($titulo, 200);
    }
}
