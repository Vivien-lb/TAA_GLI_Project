(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('StageDialogController', StageDialogController);

    StageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stage', 'Convention', 'Company'];

    function StageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Stage, Convention, Company) {
        var vm = this;

        vm.stage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.conventions = Convention.query({filter: 'stage-is-null'});
        $q.all([vm.stage.$promise, vm.conventions.$promise]).then(function() {
            if (!vm.stage.convention || !vm.stage.convention.id) {
                return $q.reject();
            }
            return Convention.get({id : vm.stage.convention.id}).$promise;
        }).then(function(convention) {
            vm.conventions.push(convention);
        });
        vm.companies = Company.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stage.id !== null) {
                Stage.update(vm.stage, onSaveSuccess, onSaveError);
            } else {
                Stage.save(vm.stage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tryApp:stageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.soonEnding = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
