var db_string = 'mongodb://127.0.0.1/';

var mongoose = require('mongoose').connect(db_string);

var db = mongoose.connection;

db.on('error', console.error.bind(console, 'Erro ao conectar no banco'))

db.once('open', function() {

	var teacherSchema = mongoose.Schema({
		name : {
			type: String,
			required: true
		},
		email: {
			type: String,
			required: true
		},
		password: {
			type: String,
			required: true
		},
		courses: {
			type: Array
		}
	});

	exports.Teacher = mongoose.model('Teacher', teacherSchema);
});