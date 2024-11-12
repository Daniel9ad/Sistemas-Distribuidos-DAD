using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

/// <summary>
/// Descripción breve de Persona
/// </summary>
public class Persona
{
    public string ci;
    public string nombres;
    public string apellidos;
    public bool esBachiller;

    public Persona()
    {
    }

    public Persona(string ci, string nombres, string apellidos, bool esBachiller)
    {
        this.ci = ci;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.esBachiller = esBachiller;
    }
}