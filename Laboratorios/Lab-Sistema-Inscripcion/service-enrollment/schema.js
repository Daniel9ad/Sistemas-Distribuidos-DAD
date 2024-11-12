const { GraphQLObjectType, GraphQLString, GraphQLSchema, GraphQLList, GraphQLNonNull } = require('graphql');
const { Matricula } = require('./database'); // Asegúrate de que el modelo Agenda esté correctamente exportado desde tu archivo de base de datos

// Definición del tipo Agenda en GraphQL
const MatriculaType = new GraphQLObjectType({
  name: 'Matricula',
  fields: {
    id: { type: GraphQLString },
    usuario_id: { type: GraphQLString },
    curso_id: { type: GraphQLString }
  }
});

// Definición de las consultas (queries)
const RootQuery = new GraphQLObjectType({
  name: 'RootQueryType',
  fields: {
    matriculas: {
      type: new GraphQLList(MatriculaType),
      resolve(parent, args) {
        // Obtener todas las agendas
        return Matricula.findAll();
      }
    },
    matricula: {
      type: MatriculaType,
      args: { id: { type: GraphQLString } },
      resolve(parent, args) {
        // Buscar una agenda por ID
        return Matricula.findByPk(args.id);
      }
    }
  }
});

// Definición de las mutaciones
const Mutation = new GraphQLObjectType({
  name: 'Mutation',
  fields: {
    addMatricula: {
      type: MatriculaType,
      args: {
        usuario_id: { type: new GraphQLNonNull(GraphQLString) },
        curso_id: { type: new GraphQLNonNull(GraphQLString) }
      },
      resolve(parent, args) {
        // Crear una nueva agenda
        return Matricula.create({
          usuario_id: args.usuario_id,
          curso_id: args.curso_id
        });
      }
    },
    updateMatricula: {
      type: MatriculaType,
      args: {
        id: { type: new GraphQLNonNull(GraphQLString) },
        usuario_id: { type: GraphQLString },
        curso_id: { type: GraphQLString }
      },
      resolve(parent, args) {
        // Actualizar una agenda existente
        return Matricula.findByPk(args.id)
          .then(matricula => {
            if (!matricula) {
              throw new Error('Matricula no encontrada');
            }
            return matricula.update({
              usuario_id: args.usuario_id !== undefined ? args.usuario_id : agenda.usuario_id,
              curso_id: args.curso_id !== undefined ? args.curso_id : agenda.curso_id
            });
          });
      }
    }
  }
});

// Exporta el esquema GraphQL
module.exports = new GraphQLSchema({
  query: RootQuery,
  mutation: Mutation
});
