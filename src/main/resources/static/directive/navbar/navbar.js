angular.module('static').directive('navbar', function() {
	return {
		restrict: 'E',
		replace: true,
        transclude: true,
		templateUrl: 'directive/navbar/navbar.html',
	};
});
