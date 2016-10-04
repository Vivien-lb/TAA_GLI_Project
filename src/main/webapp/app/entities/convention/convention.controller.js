(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('ConventionController', ConventionController);

    ConventionController.$inject = ['$scope', '$state', 'Convention'];

    function ConventionController ($scope, $state, Convention) {
        var vm = this;
        
        vm.conventions = [];

        loadAll();

        function loadAll() {
            Convention.query(function(result) {
                vm.conventions = result;
            });
        }
    }
})();
