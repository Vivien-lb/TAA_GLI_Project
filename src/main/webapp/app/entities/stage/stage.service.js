(function() {
    'use strict';
    angular
        .module('tryApp')
        .factory('Stage', Stage);

    Stage.$inject = ['$resource', 'DateUtils'];

    function Stage ($resource, DateUtils) {
        var resourceUrl =  'api/stages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                        data.soonEnding = DateUtils.convertDateTimeFromServer(data.soonEnding);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
