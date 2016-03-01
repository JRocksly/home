angular.module('static').constant("BASE_URL","/rest/elements/");

//TODO Non sta funzionando il disable degli elementi non selezionati...
//TODO Collegare il delete
angular.module('static').controller('AdminCtrl',['$scope', 'AdminService', '$uibModal', function($scope, AdminService, $uibModal){

	$scope.causalList = [];
	$scope.categoryList = [];
	$scope.subcategoryList = [];

	$scope.selectedCausal = {};
	$scope.selectedCategory = {};
	$scope.selectedSubCategory = {};

	$scope.categoryActive = false;
	$scope.subcategoryActive = false;

	$scope.getList = function(type) {
		AdminService.getList(type).then(
			function(payload){
				if(type === 'causal') {
					$scope.causalList = payload.data;
					$scope.categoryList = [];
					$scope.subcategoryList = [];
					$scope.selectedCategory = {};
					$scope.selectedSubCategory = {};
					$scope.categoryActive = false;
					$scope.subcategoryActive = false;
				}
			},
			function(err){
				console.log(err);
			}
			);
	};

	$scope.getList('causal');

	$scope.select = function(type, index) {
		if(type === 'causal') {
			$scope.selectedCausal = $scope.causalList[index];
			$scope.getChildsList(type, $scope.selectedCausal.id);
		}else if(type === 'category') {
			$scope.selectedCategory = $scope.categoryList[index];
			$scope.getChildsList(type, $scope.selectedCategory.id);
		}else if(type === 'subcategory') {
			$scope.selectedSubCategory = $scope.subcategoryList[index];
		}
	};

	$scope.getChildsList = function(type, id) {
		AdminService.getChildsList(type, id).then(
			function(payload){
				if(type === 'causal') {
					$scope.categoryList = payload.data;
					$scope.subcategoryList = [];
					$scope.selectedCategory = {};
					$scope.selectedSubCategory = {};
					$scope.categoryActive = true;
					$scope.subcategoryActive = false;
				}else if(type === 'category') {
					$scope.subcategoryList = payload.data;
					$scope.selectedSubCategory = {};
					$scope.subcategoryActive = true;
				}
			},
			function(err){
				console.log(err);
			}
			);
	};

	$scope.openModal = function(type, index) {

		$scope.selectedElement = {};

		if (typeof index !== 'undefined') {
			if(type === 'causal') {
				$scope.selectedElement =  JSON.parse(JSON.stringify($scope.selectedCausal));
			}else if(type === 'category') {
				$scope.selectedElement = JSON.parse(JSON.stringify($scope.selectedCategory));
			}else if(type === 'subcategory') {
				$scope.selectedElement = JSON.parse(JSON.stringify($scope.selectedSubCategory));
			}
		}else{
			if(type === 'category') {
				$scope.selectedElement.parentId = $scope.selectedCausal.id;
			}else if(type === 'subcategory') {
				$scope.selectedElement.parentId = $scope.selectedCategory.id;
			}
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
				
				AdminService.edit(type, selectedElement).then(
					function(payload) {
						if(type === 'causal') {
							$scope.causalList[index].label =  selectedElement.label;
						}else if(type === 'category') {
							$scope.categoryList[index].label =  selectedElement.label;
						}else if(type === 'subcategory') {
							$scope.subcategoryList[index].label =  selectedElement.label;
						}
					},
					function(err){
						console.log(err);
					}
					);

			}else{

				AdminService.create(type, selectedElement).then(
					function(payload){
						if(type === 'causal') {
							$scope.getList(type);
						}else if(type === 'category') {
							$scope.getChildsList('causal', selectedElement.parentId);
						}else if(type === 'subcategory') {
							$scope.getChildsList('category', selectedElement.parentId);
						}
					},
					function(err){
						console.log(err);
					}
					);
				
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