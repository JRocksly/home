angular.module('static').constant("CAUSAL_URL","/rest/element/causal");
angular.module('static').constant("CATEGORY_URL","/rest/element/category");
angular.module('static').constant("SUBCATEGORY_URL","/rest/element/subcategory");

angular.module('static').controller('AdminCtrl',['$scope', 'AdminService', function($scope, AdminService){
    
    $scope.causal =[];
    $scope.category =[];
    $scope.subcategory =[];
    
    $scope.getCausal = function() {

    }
    
});