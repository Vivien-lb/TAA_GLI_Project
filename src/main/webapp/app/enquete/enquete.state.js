/**
 * Created by ervan on 10/10/16.
 */
(function() {
    'use strict';

    angular
        .module('tryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('enquete', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/enquete/enquete.html',
                    controller: 'EnqueteController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();

