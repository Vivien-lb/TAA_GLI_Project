(function() {
    'use strict';
    angular
        .module('tryApp')
        .factory('Convention', Convention);

    Convention.$inject = ['$resource'];

    function Convention ($resource) {
        var resourceUrl =  'api/conventions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
