angular.module('home').directive('navbar', function() {
	return {
		restrict: 'E',
		replace: true,
        transclude: true,
		templateUrl: 'directive/navbar/navbar.html',
	};
});
