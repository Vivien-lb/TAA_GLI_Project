(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', 'Stage', 'Teacher', 'Contact'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, Stage, Teacher, Contact) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tryApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
