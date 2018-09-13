const db = require('../../config/db_config');

exports.getAll = (req, res, next) => {

    db.Teacher.find({}, function(error, teachers){

        if(error){
            res.json({error:'Não foi possível retornar os professores'});
        }else{
            res.json(teachers)
        }
    });
};

exports.getOne = (req, res, next) => {

    db.Teacher.findById(req.params.id, function (error, teacher) {
        if(error){
            res.json({error:'Não foi possível encontrar o professor'});
        }else{
            res.json(teacher)
        }
    })
};

exports.create = (req, res, next) => {
    new db.Teacher({
        name: req.body.name,
        email: req.body.email,
        password: req.body.password
    }).save(
        (error, teacher) => {
            if (error) {
                res.status(400);
                res.send('An error ocurred.');
            } else {
                res.json(teacher);
            }
        }
    );
};

exports.update = (req, res, next) => {
    db.Teacher.findByIdAndUpdate(req.params.id, req.body, (error, result) => {
        if (error) {
            res.status(400);
            res.send('An error ocurred.');
        } else {
            res.json(result);
        }
    });
};

exports.remove = (req, res, next) => {
    db.Teacher.findByIdAndRemove(req.params.id, (error, result) => {
        if (error) {
            res.status(400);
            res.send('An error ocurred.');
        } else {
            res.json(result);
        }
    });
};
