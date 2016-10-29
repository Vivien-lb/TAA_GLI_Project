(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('MyHomeController', MyHomeController);

    MyHomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function MyHomeController ($scope, Principal, LoginService, $state) {
            $scope.firstName = "Ervan";
            $scope.lastName = "Doe";
}})();
