(function() {
    'use strict';

    angular
        .module('kukulkanmongoApp')
        .controller('PruebaDetailController', PruebaDetailController);

    PruebaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Prueba'];

    function PruebaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Prueba) {
        var vm = this;

        vm.prueba = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('kukulkanmongoApp:pruebaUpdate', function(event, result) {
            vm.prueba = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
