import swaggerAutogen from 'swagger-autogen'
import config from './config.js'


const doc = {
  info: {
    title: 'Order-service',
    description: 'Order MS',
  },
  host: `localhost:${config.get('APP_PORT')}`,
  schemes: ['http'],
};

const outputFile = './swagger.json';
const endpointsFiles = ['./src/orders/api/v0/orders.api.js'];

/* NOTE: if you use the express Router, you must pass in the 
   'endpointsFiles' only the root file where the route starts,
   such as index.js, app.js, routes.js, ... */

   swaggerAutogen()(outputFile, endpointsFiles, doc).then(async () => {
    await import('./app.js'); // Your project's root file
  });
