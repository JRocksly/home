angular.module('static').factory('AdminService', ['$http', '$q', 'BASE_URL', function($http, $q, BASE_URL) {

	var AdminService = {
        
        getList: function(type) {
            var deferred = $q.defer();
            $http({
              method: 'GET',
              url: BASE_URL + type
            }).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    deferred.reject(err);
                }
            );
            return deferred.promise;
        },

        getChildsList: function(type, id) {
            var deferred = $q.defer();
            $http({
              method: 'GET',
              url: BASE_URL + type + '/childs/' + id
            }).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    deferred.reject(err);
                }
            );
            return deferred.promise;
        },

        create: function(type, data) {
            var deferred = $q.defer();
            $http({
              method: 'POST',
              url: BASE_URL + type,
              data: data
            }).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    deferred.reject(err);
                }
            );
            return deferred.promise;
        },

        edit: function(type, data) {
            var deferred = $q.defer();
            $http({
              method: 'PUT',
              url: BASE_URL + type + '/id/' + data.id,
              data: data.label
            }).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    deferred.reject(err);
                }
            );
            return deferred.promise;
        },

    };

	return AdminService;
}]);