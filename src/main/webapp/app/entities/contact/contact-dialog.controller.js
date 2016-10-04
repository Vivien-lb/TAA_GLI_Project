(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('ContactDialogController', ContactDialogController);

    ContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contact', 'Company', 'Student', 'Teacher'];

    function ContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Contact, Company, Student, Teacher) {
        var vm = this;

        vm.contact = entity;
        vm.clear = clear;
        vm.save = save;
        vm.companies = Company.query();
        vm.students = Student.query();
        vm.teachers = Teacher.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contact.id !== null) {
                Contact.update(vm.contact, onSaveSuccess, onSaveError);
            } else {
                Contact.save(vm.contact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tryApp:contactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
