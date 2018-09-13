const teacherService = require('./teacher.service');

module.exports = (app) => {

    app.route('/teacher')
        .get(teacherService.getAll)
        .post(teacherService.create);

    app.route('/teacher/:id')
        .get(teacherService.getOne)
        .delete(teacherService.remove)
        .put(teacherService.update);
};
