angular.module('home').factory('alertService', ['$uibModal', function ($uibModal) {

	var alertService = {
	
		openAlert: function (type, message) {
			var alert = {};
			alert.type = type;
			alert.message = message;
			var modalInstance = $uibModal.open({
				animation: true,
				size: 'sm',
				templateUrl: 'directive/alert/alert.html',
				controller: 'alertController',
				resolve: {
					alert: function () {
						return alert;
					}
				}
			});
		}

	};

	return alertService;

}]);

angular.module('home').controller('alertController', function ($scope, $uibModalInstance, alert) {

	$scope.alert = alert;

	$scope.close = function () {
		$uibModalInstance.dismiss('close');
	};

});