angular.module('static').factory('AdminService', ['$http', '$q', 'CAUSAL_URL', function($http, $q, CAUSAL_URL) {

	var AdminService = {
        
        getCausalList: function() {
            var deferred = $q.defer();
            $http({
              method: 'GET',
              url: CAUSAL_URL
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

        createCausal: function(data) {
            var deferred = $q.defer();
            $http({
              method: 'POST',
              url: CAUSAL_URL,
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

        editCausal: function(data) {
            var deferred = $q.defer();
            $http({
              method: 'PUT',
              url: CAUSAL_URL + '/id/' + data.id,
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