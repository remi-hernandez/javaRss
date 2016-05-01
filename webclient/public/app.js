angular.module('app', [
	'ngRoute',
    'ngStorage',
    'app.home',
    ])


.config(['$routeProvider', '$locationProvider',
    function ($routeProvider, $locationProvider){

        $routeProvider.otherwise({
            redirectTo: '/'
        });
    }])

// .run(['$rootScope', '$localStorage', '$http', function ($rootScope, $localStorage, $http) {
//     $rootScope.loader = true;
//     $rootScope.$on( "$routeChangeStart", function(event, next, current) {

//     });
// }]);