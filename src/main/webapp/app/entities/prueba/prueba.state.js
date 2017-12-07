(function() {
    'use strict';

    angular
        .module('kukulkanmongoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prueba', {
            parent: 'entity',
            url: '/prueba?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanmongoApp.prueba.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prueba/pruebas.html',
                    controller: 'PruebaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('prueba');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('prueba-detail', {
            parent: 'prueba',
            url: '/prueba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kukulkanmongoApp.prueba.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prueba/prueba-detail.html',
                    controller: 'PruebaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('prueba');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Prueba', function($stateParams, Prueba) {
                    return Prueba.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prueba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prueba-detail.edit', {
            parent: 'prueba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prueba/prueba-dialog.html',
                    controller: 'PruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prueba', function(Prueba) {
                            return Prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prueba.new', {
            parent: 'prueba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prueba/prueba-dialog.html',
                    controller: 'PruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                edad: null,
                                numeroCredencial: null,
                                sueldo: null,
                                impuesto: null,
                                impuestoDetalle: null,
                                activo: false,
                                fechaLocalDate: null,
                                fechaZoneDateTime: null,
                                imagen: null,
                                imagenContentType: null,
                                imagenAnyBlob: null,
                                imagenAnyBlobContentType: null,
                                imagenBlob: null,
                                imagenBlobContentType: null,
                                desc: null,
                                instante: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prueba', null, { reload: 'prueba' });
                }, function() {
                    $state.go('prueba');
                });
            }]
        })
        .state('prueba.edit', {
            parent: 'prueba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prueba/prueba-dialog.html',
                    controller: 'PruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Prueba', function(Prueba) {
                            return Prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prueba', null, { reload: 'prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prueba.delete', {
            parent: 'prueba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prueba/prueba-delete-dialog.html',
                    controller: 'PruebaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Prueba', function(Prueba) {
                            return Prueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prueba', null, { reload: 'prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
