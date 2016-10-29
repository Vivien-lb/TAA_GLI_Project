/**
 * Created by ervan on 12/10/16.
 */
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
        $stateProvider.state('mail', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/mail/mail.html',
                    controller: 'MailController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
