angular.module('static').constant("BASE_URL","/rest/elements/");

angular.module('static').controller('AdminCtrl',['$scope', 'CategorizationElementService', '$uibModal', 'alertService', function($scope, CategorizationElementService, $uibModal, alertService){

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
			size: 'sm',
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
				
				CategorizationElementService.edit(type, selectedElement).then(
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
						alertService.openAlert("error", "Non ho idea di cosa sia successo O_O");
					}
				);

			}else{

				CategorizationElementService.create(type, selectedElement).then(
					function(payload){
						reload(type, selectedElement.parentId);
					},
					function(err){
						alertService.openAlert("error", "Non ho idea di cosa sia successo O_O");
					}
				);
				
			}

		});

	};

	$scope.remove = function(type, index) {
		var toRemove = {};
		if(type === 'causal') {
			toRemove = $scope.causalList[index];
		}else if(type === 'category') {
			toRemove = $scope.categoryList[index];
		}else if(type === 'subcategory') {
			toRemove = $scope.subcategoryList[index];
		}
		console.log(toRemove);
		CategorizationElementService.delete(type, toRemove.id).then(
			function(payload) {
				reload(type, toRemove.parentId);
			},
			function(err){
				alertService.openAlert("error", "Non ho idea di cosa sia successo O_O");
			}
		);
	};

	var reload = function(type, id) {
		if(type === 'causal') {
			$scope.getList(type);
		}else if(type === 'category') {
			$scope.getChildsList('causal', id);
		}else if(type === 'subcategory') {
			$scope.getChildsList('category', id);
		}
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