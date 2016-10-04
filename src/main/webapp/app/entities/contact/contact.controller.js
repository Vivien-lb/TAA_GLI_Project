(function() {
    'use strict';

    angular
        .module('tryApp')
        .controller('ContactController', ContactController);

    ContactController.$inject = ['$scope', '$state', 'Contact'];

    function ContactController ($scope, $state, Contact) {
        var vm = this;
        
        vm.contacts = [];

        loadAll();

        function loadAll() {
            Contact.query(function(result) {
                vm.contacts = result;
            });
        }
    }
})();
