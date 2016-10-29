/**
 * Created by ervan on 12/10/16.
 */
(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('MailController', MailController);

    MailController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Company', 'Teacher'];

    function MailController ($scope, Principal, LoginService, $state, Company, Teacher) {
        $scope.def = "Ceci est la page des mails";
        $scope.visibleTeacher = false;
        $scope.visibleCompany = false;

        $scope.findTeacher = [];
        findTeacher()


        $scope.visibleT = function(){
            $scope.visibleTeacher = !$scope.visibleTeacher;
        }

        $scope.findCompanies = function() {
            Company.query(function (result) {
                $scope.companies = result;
                $scope.visibleCompany = !$scope.visibleCompany;
            });
        }
        function findTeacher() {
            Teacher.query(function (result) {
                $scope.findTeacher = result;
                $scope.visibleTeacher = !$scope.visibleTeacher;
            });
        }
    }
})();
