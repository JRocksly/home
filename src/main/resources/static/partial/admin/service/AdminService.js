angular.module('static').factory('AdminService', ['$http', '$q', 'BASE_URL', 'alertService', function($http, $q, BASE_URL, alertService) {

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
                    if(!errorHandling(err)) {
                        deferred.reject(err);
                    }
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
                   if(!errorHandling(err)) {
                        deferred.reject(err);
                    }
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
                    if(!errorHandling(err)) {
                        deferred.reject(err);
                    }
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
                    if(!errorHandling(err)) {
                        deferred.reject(err);
                    }
                }
            );
            return deferred.promise;
        },

        delete: function(type, id) {
            var deferred = $q.defer();
            $http({
              method: 'DELETE',
              url: BASE_URL + type + '/id/' + id,
            }).then(
                function(payload){
                    deferred.resolve(payload);
                },
                function(err){
                    if(!errorHandling(err)) {
                        deferred.reject(err);
                    }
                }
            );
            return deferred.promise;
        }

    };

    var errorHandling = function(err) {
        if(err.status === 404) {
            serverNonRaggiungibile();
            return true;
        }else if(err.status === 500) {
            erroreInterno();
            return true;
        }else if(err.status === 400 || err.status === 409) {
            alertService.openAlert(err.data.type, err.data.message);
            return true;
        }
        return false;
    };

    var serverNonRaggiungibile = function() {
        alertService.openAlert("error", "Qualcosa non va sul server...!");
    };

    var erroreInterno = function() {
        alertService.openAlert("error", "Errore brutto! Chiedi spiegazioni!");
    };

	return AdminService;
}]);