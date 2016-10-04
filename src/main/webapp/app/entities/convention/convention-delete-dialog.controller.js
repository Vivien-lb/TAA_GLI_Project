(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('ConventionDeleteController',ConventionDeleteController);

    ConventionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Convention'];

    function ConventionDeleteController($uibModalInstance, entity, Convention) {
        var vm = this;

        vm.convention = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Convention.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
