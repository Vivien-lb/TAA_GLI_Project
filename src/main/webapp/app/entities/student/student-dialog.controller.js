(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('StudentDialogController', StudentDialogController);

    StudentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Student', 'Stage', 'Teacher', 'Contact'];

    function StudentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Student, Stage, Teacher, Contact) {
        var vm = this;

        vm.student = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.stages = Stage.query({filter: 'student-is-null'});
        $q.all([vm.student.$promise, vm.stages.$promise]).then(function() {
            if (!vm.student.stage || !vm.student.stage.id) {
                return $q.reject();
            }
            return Stage.get({id : vm.student.stage.id}).$promise;
        }).then(function(stage) {
            vm.stages.push(stage);
        });
        vm.teachers = Teacher.query();
        vm.contacts = Contact.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.student.id !== null) {
                Student.update(vm.student, onSaveSuccess, onSaveError);
            } else {
                Student.save(vm.student, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tryApp:studentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
