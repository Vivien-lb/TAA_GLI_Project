(function() {
    'use strict';
    angular
        .module('tryApp')
        .factory('Student', Student);

    Student.$inject = ['$resource', 'DateUtils'];

    function Student ($resource, DateUtils) {
        var resourceUrl =  'api/students/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthDate = DateUtils.convertDateTimeFromServer(data.birthDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
