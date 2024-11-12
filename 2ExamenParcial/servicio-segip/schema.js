const { GraphQLObjectType, GraphQLString, GraphQLSchema, GraphQLList, GraphQLNonNull } = require('graphql');
const { Persona } = require('./database');

// Definición del tipo Persona en GraphQL
const PersonaType = new GraphQLObjectType({
  name: 'Persona',
  fields: {
    ci: { type: GraphQLString },
    nombres: { type: GraphQLString },
    primer_apellido: { type: GraphQLString },
    segundo_apellido: { type: GraphQLString }
  }
});

// Definición de las consultas (queries)
const RootQuery = new GraphQLObjectType({
  name: 'RootQueryType',
  fields: {
    personas: {
      type: new GraphQLList(PersonaType),
      resolve(parent, args) {
        return Persona.findAll();
      }
    },
    persona: {
      type: PersonaType,
      args: { ci: { type: GraphQLString } },
      resolve(parent, args) {
        return Persona.findByPk(args.ci);
      }
    }
  }
});

// Definición de las mutaciones
const Mutation = new GraphQLObjectType({
  name: 'Mutation',
  fields: {
    addPersona: {
      type: PersonaType,
      args: {
        ci: { type: new GraphQLNonNull(GraphQLString) },
        nombres: { type: new GraphQLNonNull(GraphQLString) },
        primer_apellido: { type: new GraphQLNonNull(GraphQLString) },
        segundo_apellido: { type: new GraphQLNonNull(GraphQLString) }
      },
      resolve(parent, args) {
        return Persona.create({
          ci: args.ci,
          nombres: args.nombres,
          primer_apellido: args.primer_apellido,
          segundo_apellido: args.segundo_apellido
        });
      }
    },
    updatePersona: {
      type: PersonaType,
      args: {
        ci: { type: new GraphQLNonNull(GraphQLString) },
        nombres: { type: GraphQLString },
        primer_apellido: { type: GraphQLString },
        segundo_apellido: { type: GraphQLString },
      },
      resolve(parent, args) {
        return Persona.findByPk(args.ci)
          .then(persona => {
            if (!persona) {
              throw new Error('Persona no encontrada');
            }
            return persona.update({
              nombre: args.nombre !== undefined ? args.nombre : persona.nombre,
              primer_apellido: args.primer_apellido !== undefined ? args.primer_apellido : persona.primer_apellido,
              segundo_apellido: args.segundo_apellido !== undefined ? args.segundo_apellido : persona.segundo_apellido,
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
