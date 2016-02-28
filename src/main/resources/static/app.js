angular.module('static', ['ui.bootstrap','ui.utils','ngRoute','ngAnimate','angular.panels']);

angular.module('static').config(function($routeProvider) {

    $routeProvider.when('/home',{templateUrl: 'partial/home/home.html'});
    $routeProvider.when('/admin',{templateUrl: 'partial/admin/admin.html'});
    $routeProvider.when('/expense/:id',{templateUrl: 'partial/expense/expense.html'});
    /* Add New Routes Above */
    $routeProvider.otherwise({redirectTo:'/home'});

});

angular.module('static').run(function($rootScope) {

    $rootScope.safeApply = function(fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            this.$apply(fn);
        }
    };

});
