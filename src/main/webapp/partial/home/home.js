angular.module('home').controller('HomeCtrl', ['$scope', 'alertService', function($scope, alertService){

	$scope.success = function() {
		alertService.openAlert("success", "I am a great success!");
	};

	$scope.info = function() {
		alertService.openAlert("info", "Hey! Let me tell you something!");
	};

	$scope.warning = function() {
		alertService.openAlert("warning", "Watch out!");
	};

	$scope.error = function() {
		alertService.openAlert("error", "I am an  epic fail!");
	};

}]);