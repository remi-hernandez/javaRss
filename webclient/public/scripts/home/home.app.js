
angular.module('app.home', [])

.config(['$routeProvider',
    function ($routeProvider){
        $routeProvider
        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeCtrl as Home'
        });
    }]);