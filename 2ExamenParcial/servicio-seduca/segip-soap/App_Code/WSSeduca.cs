using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;


/// <summary>
/// Descripción breve de WSSegip
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// Para permitir que se llame a este servicio web desde un script, usando ASP.NET AJAX, quite la marca de comentario de la línea siguiente. 
// [System.Web.Script.Services.ScriptService]
public class WSSeduca : System.Web.Services.WebService
{
    public List<Persona> personas = new List<Persona>();
    public WSSeduca()
    {
        Persona persona1 = new Persona("79845133", "Juan", "Peres Duran", true);
        Persona persona2 = new Persona("1323", "Daniel", "Duran", true);
        personas.Add(persona1);
        personas.Add(persona2);
    }

    [WebMethod]
    public bool obtenerCalificaciones(string ci)
    {
        foreach (Persona persona in personas)
        {
            if (persona.ci.Equals(ci))
            {   
                if (persona.esBachiller)
                {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
