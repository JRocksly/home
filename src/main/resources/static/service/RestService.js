angular.module('static').factory('RestService',['$http', '$q', function($http,$q) {

	var RestService = {
        
        //Get all
        list: function(modelUrl, pathVariables) {            
            var deferred = $q.defer();
            $http.get(modelUrl, {params: pathVariables}).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    deferred.reject(err);
                }
            );
            return deferred.promise;
        }
        
    };

	return RestService;
    
}]);