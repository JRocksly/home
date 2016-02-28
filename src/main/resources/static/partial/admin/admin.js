angular.module('static').constant("CAUSAL_URL","http://localhost:7001/rest/element/causal");
angular.module('static').constant("CATEGORY_URL","http://localhost:7001/rest/element/category");
angular.module('static').constant("SUBCATEGORY_URL","http://localhost:7001/rest/element/subcategory");

angular.module('static').controller('AdminCtrl',['$scope', 'AdminService', '$uibModal', function($scope, AdminService, $uibModal){

	$scope.causalList =[];
	$scope.categoryList =[];
	$scope.subcategoryList =[];

	$scope.selectedCausal = {};
	$scope.selectedCategory = {};
	$scope.selectedSubCategory = {};

	$scope.getCausalList = function() {
		AdminService.getCausalList().then(
			function(payload){
				$scope.causalList = payload.data;
			},
			function(err){
				console.log(err);
			}
		);
	};

	$scope.getCausalList();

	$scope.selectCausal = function(index) {
		$scope.selectedCausal = $scope.causalList[index];
	};

	$scope.openModal = function (type, index) {

		$scope.selectedElement = {};

		if (typeof index !== 'undefined') {
			if(type === 'causal') {
				$scope.selectedElement =  JSON.parse(JSON.stringify($scope.causalList[index]));
			}/*else if(type === 'category') {
				$scope.selectedElement = $scope.categoryList[index];
			}else{
				$scope.selectedElement = $scope.subcategoryList[index];
			}*/
		}else{
			/*if(type === 'category') {
				$scope.selectedElement.parentId = $scope.selectedCausal.id;
			}else if(type === 'subcategory') {
				$scope.selectedElement.parentId = $scope.selectedCategory.id;
			}*/
		}
		
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: 'CategorizationElementModal.html',
			controller: 'CategorizationElementModalCtrl',
			resolve: {
				selectedElement: function () {
					return $scope.selectedElement;
				}
			}
		});

		modalInstance.result.then(function (selectedElement) {
			if(typeof selectedElement.id !== 'undefined') {
				if(type === 'causal') {
					AdminService.editCausal(selectedElement).then(
						function(payload){
		                    $scope.causalList[index].label =  selectedElement.label;
		                },
		                function(err){
		                    console.log(err);
		                }
					);
				}/*else if(type === 'category') {
					$scope.selectedElement = $scope.categoryList[index];
				}else{
					$scope.selectedElement = $scope.subcategoryList[index];
				}*/
			}else{
				if(type === 'causal') {
					AdminService.createCausal(selectedElement).then(
						function(payload){
		                    $scope.getCausalList();
		                },
		                function(err){
		                    console.log(err);
		                }
					);
				}/*else if(type === 'category') {
					$scope.selectedElement = $scope.categoryList[index];
				}else{
					$scope.selectedElement = $scope.subcategoryList[index];
				}*/
			}
		});

	};

}]);

angular.module('static').controller('CategorizationElementModalCtrl', function ($scope, $uibModalInstance, selectedElement) {

	$scope.selectedElement = selectedElement;

	$scope.ok = function () {
		$uibModalInstance.close($scope.selectedElement);
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};

});