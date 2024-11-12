using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net.Http;
using Newtonsoft.Json;

namespace OficinaTramites
{
    [Serializable]
    public class Titulo
    {
        public string ci { get; set; }
        public string nombre_completo { get; set; }
        public string titulo { get; set; }
        public string fecha_emision { get; set; }
    }
    [Serializable]
    public class Calificaciones
    {
        public string ci { get; set; }
        public string apellidos { get; set; }
        public string nombres { get; set; }
        public bool esBachiller { get; set; }
    }

    [Serializable]
    public class Persona
    {
        public string ci { get; set; }
        public string nombres { get; set; }
        public string primerApellido { get; set; }
        public string segundoApellido { get; set; }
    }
    [Serializable]
    public class GraphQLResponse
    {
        public Data data { get; set; }
    }
    [Serializable]
    public class Data
    {
        public Persona persona { get; set; }
    }
    public partial class Form1 : Form
    {
        private static readonly HttpClient clientHttp = new HttpClient();
        public Form1()
        {
            InitializeComponent();
        }

        private void buttonEmitir_Click(object sender, EventArgs e)
        {
            string ci = txtCI.Text;
            string nombres = txtNombres.Text;
            string primerApellido = txtPirmerApellido.Text;
            string segundoApellido = txtSegundoApellido.Text;
            string titulo = txtTitulo.Text;

            string url = "http://localhost:4000/graphql";
            string query = @"
            query($ci: String!) {
                persona(ci: $ci) {
                    ci
                    nombres
                    primer_apellido
                    segundo_apellido
                }
            }";

            var requestBody = new
            {
                query = query,
                variables = new { ci = ci }
            };
            var content = new StringContent(JsonConvert.SerializeObject(requestBody), Encoding.UTF8, "application/json");
            var response = clientHttp.PostAsync(url, content).Result;
            var result = response.Content.ReadAsStringAsync().Result;
            GraphQLResponse graphQLResponse = JsonConvert.DeserializeObject<GraphQLResponse>(result);
            Persona persona = graphQLResponse.data.persona;

            if (persona != null)
            {
                labelResult.Text = "Datos Existentes...\n";
                //WSSeduca.WSSeducaSoapClient client = new WSSeduca.WSSeducaSoapClient();
                //bool existe = client.obtenerCalificacionse(ci);
                //labelResult.Text += "Persona: " + persona.ci + " - " + persona.nombres + " - " + persona.primerApellido + "\n";
                //Calificaciones calificaciones = getCalificaciones(ci);
                //labelResult.Text += "Es bachiller: " + calificaciones.ToString() + "\n";
                //Titulo titulo_a = getTitulo(ci);
                //labelResult.Text += "Titulo: " + titulo_a.nombre_completo + "\n";
            } else
            {
                labelResult.Text = "No encontrado...\n";
            }
        }
        private Titulo getTitulo(string ci)
        {
            string url = "http://localhost:8000/api/titulo/"+ci;
            var response = clientHttp.GetAsync(url).Result;
            var result = response.Content.ReadAsStringAsync().Result;
            Titulo titulo = JsonConvert.DeserializeObject<Titulo>(result);
            return titulo;
        }
    }
}
