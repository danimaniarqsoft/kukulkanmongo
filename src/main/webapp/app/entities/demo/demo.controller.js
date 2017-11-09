(function() {
    'use strict';

    angular
        .module('kukulkanmongoApp')
        .controller('DemoController', DemoController);

    DemoController.$inject = ['DataUtils', 'Demo'];

    function DemoController(DataUtils, Demo) {

        var vm = this;

        vm.demos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Demo.query(function(result) {
                vm.demos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
