(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('ConventionDialogController', ConventionDialogController);

    ConventionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Convention'];

    function ConventionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Convention) {
        var vm = this;

        vm.convention = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.convention.id !== null) {
                Convention.update(vm.convention, onSaveSuccess, onSaveError);
            } else {
                Convention.save(vm.convention, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tryApp:conventionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
