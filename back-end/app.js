var app = require('./config/app_config.js');
const consign = require('consign');

consign()
    .include('./api/')
    .into(app);