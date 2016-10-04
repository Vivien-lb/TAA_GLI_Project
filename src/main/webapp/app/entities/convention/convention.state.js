(function() {
    'use strict';

    angular
        .module('tryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convention', {
            parent: 'entity',
            url: '/convention',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Conventions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convention/conventions.html',
                    controller: 'ConventionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('convention-detail', {
            parent: 'entity',
            url: '/convention/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Convention'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convention/convention-detail.html',
                    controller: 'ConventionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Convention', function($stateParams, Convention) {
                    return Convention.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'convention',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('convention-detail.edit', {
            parent: 'convention-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convention/convention-dialog.html',
                    controller: 'ConventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convention', function(Convention) {
                            return Convention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convention.new', {
            parent: 'convention',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convention/convention-dialog.html',
                    controller: 'ConventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sujet: null,
                                studentSignature: null,
                                contactSignature: null,
                                universitySignature: null,
                                salary: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('convention', null, { reload: 'convention' });
                }, function() {
                    $state.go('convention');
                });
            }]
        })
        .state('convention.edit', {
            parent: 'convention',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convention/convention-dialog.html',
                    controller: 'ConventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convention', function(Convention) {
                            return Convention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convention', null, { reload: 'convention' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convention.delete', {
            parent: 'convention',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convention/convention-delete-dialog.html',
                    controller: 'ConventionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Convention', function(Convention) {
                            return Convention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convention', null, { reload: 'convention' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
