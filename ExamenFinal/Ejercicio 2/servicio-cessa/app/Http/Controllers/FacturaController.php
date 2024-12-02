<?php

namespace App\Http\Controllers;

use App\Models\Factura;
use App\Models\Persona;
use Illuminate\Http\Request;

class FacturaController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(string $ci)
    {
        $persona = Persona::where('ci', $ci)->first();
        $facturas = $persona->facturas;
        return response()->json($facturas, 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $factura = Factura::find($id);
        if (!$factura) {
            return response()->json([
                'error' => 'Factura no encontrada.'
            ], 404);
        }
        $factura->estado = 'pagado';
        $factura->save();
        return response()->json([
            'message' => 'factura pagada',
            'factura' => $factura,
        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }
}
