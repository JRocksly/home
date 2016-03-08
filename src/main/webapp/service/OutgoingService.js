angular.module('home').constant("BASE_URL_OUTGOING","/rest/outgoings");

angular.module('home').factory('OutgoingService', ['$http', '$q', 'BASE_URL_OUTGOING', 'alertService', function($http, $q, BASE_URL_OUTGOING, alertService) {

	var OutgoingService = {

		create: function(data) {
            var deferred = $q.defer();
            $http({
              method: 'POST',
              url: BASE_URL_OUTGOING,
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

	return OutgoingService;
	
}]);