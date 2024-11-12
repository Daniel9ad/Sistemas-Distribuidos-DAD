<?php

namespace App\Http\Controllers;

use App\Models\Course;
use App\Services\RabbitMQService;
use Illuminate\Http\Request;

class CourseController extends Controller
{

    protected $rabbitMQService;

    // Inyectamos el servicio RabbitMQService en el constructor
    public function __construct(RabbitMQService $rabbitMQService)
    {
        $this->rabbitMQService = $rabbitMQService;
    }

    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        return Course::all();
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $curso = Course::create($request->all());
        $this->rabbitMQService->publishMessage($curso->json_encode, 'course_notifications');
        return response()->json($curso, 201);
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        $curso = Course::find($id);
        return response()->json($curso, 200);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $curso = Course::find($id);
        $input = $request->all();
        $curso->update($input);
        return response()->json($curso, 200);
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $curso = Course::find($id);
        $curso->delete();
        return response()->json($curso, 200);
    }
}
