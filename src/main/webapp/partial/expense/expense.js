angular.module('home').controller('ExpenseCtrl', ['$scope', 'CategorizationElementService', 'alertService', function($scope, CategorizationElementService, alertService){

	$scope.outgoing = {};

	$scope.causalList = [];
	$scope.categoryList = [];
	$scope.subcategoryList = [];

	$scope.selectedCausal = {};
	$scope.selectedCategory = {};
	$scope.selectedSubCategory = {};

	var categoryActive = false;
	var subcategoryActive = false;

	$scope.isActive = function(type) {
		if(type === 'category') {
			return categoryActive;
		}else if(type === 'subcategory') {
			return subcategoryActive;
		}
		return false;
	};

	$scope.getList = function(type) {
		CategorizationElementService.getList(type).then(
			function(payload){
				if(type === 'causal') {
					$scope.causalList = payload.data;
					$scope.categoryList = [];
					$scope.subcategoryList = [];
					$scope.selectedCategory = {};
					$scope.selectedSubCategory = {};
					categoryActive = false;
					subcategoryActive = false;
				}
			},
			function(err){
				alertService.openAlert("error", "Non ho idea di cosa sia successo O_O");
			}
			);
	};

	$scope.getList('causal');

	$scope.select = function(type) {
		if(type === 'causal') {
			$scope.outgoing.causalId = $scope.selectedCausal.id;
			$scope.getChildsList(type, $scope.selectedCausal.id);
		}else if(type === 'category') {
			$scope.outgoing.categoryId = $scope.selectedCategory.id;
			$scope.getChildsList(type, $scope.selectedCategory.id);
		}else if(type === 'subcategory') {
			$scope.outgoing.subcategoryId = $scope.selectedSubCategory.id;
		}
	};

	$scope.getChildsList = function(type, id) {
		CategorizationElementService.getChildsList(type, id).then(
			function(payload){
				if(type === 'causal') {
					$scope.categoryList = payload.data;
					$scope.subcategoryList = [];
					$scope.selectedCategory = {};
					$scope.selectedSubCategory = {};
					categoryActive = true;
					subcategoryActive = false;
				}else if(type === 'category') {
					$scope.subcategoryList = payload.data;
					$scope.selectedSubCategory = {};
					subcategoryActive = true;
				}
			},
			function(err){
				alertService.openAlert("error", "Non ho idea di cosa sia successo O_O");
			}
			);
	};

	var date = new Date();
	$scope.timezone = ((date.getTimezoneOffset() / 60) * -100);
	$scope.format = 'dd/MM/yyyy';
	var validDate = true;

	$scope.today = function() {
		$scope.outgoing.date = new Date();
	};

	$scope.today();

	$scope.clear = function() {
		$scope.outgoing.date = null;
	};

	$scope.dateOptions = {
		formatYear: 'yyyy',
		formatMonth: 'MM',
		formatDay: 'dd',
		startingDay: 1,
		language: 'it'
	};

	$scope.open = function() {
		$scope.popup.opened = true;
	};

	$scope.popup = {
		opened: false
	};

	$scope.isValidDate = function() {
		return validDate;
	};

	$scope.validateDate = function() {
		// First check for the pattern
		if(!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test($scope.outgoing.date)) {
			validDate = false;
			return;
		}

	    // Parse the date parts to integers
	    var parts = $scope.outgoing.date.split("/");
	    var day = parseInt(parts[0], 10);
	    var month = parseInt(parts[1], 10);
	    var year = parseInt(parts[2], 10);

	    // Check the ranges of month and year
	    if(year < 1000 || year > 3000 || month === 0 || month > 12) {
	    validDate =  false;
	    return;
	    }

	    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

	    // Adjust for leap years
	    if(year % 400 === 0 || (year % 100 !== 0 && year % 4 === 0)) {
	    monthLength[1] = 29;
	    }

	    // Check the range of the day
	    validDate = day > 0 && day <= monthLength[month - 1];
	};

}]);

angular.module('home').directive('dateParser',function(dateFilter,$parse){
	return{
		restrict:'EAC',
		require:'?ngModel',
		link:function(scope,element,attrs,ngModel,ctrl){
			ngModel.$parsers.push(function(viewValue){
				return dateFilter(viewValue,'dd/MM/yyyy');
			});
		}
	};
});