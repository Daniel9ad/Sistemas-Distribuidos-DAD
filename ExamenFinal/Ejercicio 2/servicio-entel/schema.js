const { GraphQLObjectType, GraphQLString, GraphQLSchema, GraphQLList, GraphQLNonNull } = require('graphql');
const { Persona, Factura } = require('./database');

// Definición del tipo Persona en GraphQL
const PersonaType = new GraphQLObjectType({
  name: 'Persona',
  fields: {
    ci: { type: GraphQLString },
    nombres: { type: GraphQLString }
  }
});

const FacturaType = new GraphQLObjectType({
  name: 'Factura',
  fields: {
    id: { type: GraphQLString },
    persoanid: { type: GraphQLString },
    monto: { type: GraphQLString },
    estado: { type: GraphQLString }
  }
});

// Definición de las consultas (queries)
const RootQuery = new GraphQLObjectType({
  name: 'RootQueryType',
  fields: {
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
    pagarfactura: {
      type: FacturaType,
      args: {
        id: { type: new GraphQLNonNull(GraphQLString) },
      },
      resolve(parent, args) {
        return Factura.findByPk(args.id)
          .then(factura => {
            if (!factura) {
              throw new Error('factura no encontrada');
            }
            return factura.update({
              estado: "pagado",
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
