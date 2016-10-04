(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('TeacherDialogController', TeacherDialogController);

    TeacherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Teacher', 'Student', 'Contact'];

    function TeacherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Teacher, Student, Contact) {
        var vm = this;

        vm.teacher = entity;
        vm.clear = clear;
        vm.save = save;
        vm.students = Student.query();
        vm.contacts = Contact.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.teacher.id !== null) {
                Teacher.update(vm.teacher, onSaveSuccess, onSaveError);
            } else {
                Teacher.save(vm.teacher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tryApp:teacherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
