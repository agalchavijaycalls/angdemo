'use strict';

angular.module('example',
		[ 'ngRoute', 'ngResource', 'http-auth-interceptor', 'login' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/', {
				templateUrl : 'views/landing.html',
				controller : 'LandingPageController'
			}).when('/Examples', {
				templateUrl : 'views/Example/search.html',
				controller : 'SearchExampleController'
			}).when('/Examples/new', {
				templateUrl : 'views/Example/detail.html',
				controller : 'NewExampleController'
			}).when('/Examples/edit/:ExampleId', {
				templateUrl : 'views/Example/detail.html',
				controller : 'EditExampleController'
			}).otherwise({
				redirectTo : '/'
			});
		} ]).controller('LandingPageController',
		function LandingPageController() {
		}).controller(
		'NavController',
		function NavController($scope, $location, $http) {
			$scope.matchesRoute = function(route) {
				var path = $location.path();
				return (path === ("/" + route) || path.indexOf("/" + route
						+ "/") == 0);
			};
			$scope.logout = function() {
				$http({
					url : 'rest/users/logout',
					method : "GET"
				}).then(function(response) {
					alert('Logout Success ' + response);
					authService.loginCancelled();
				}, function(response) {
					alert('Logout failed ' + response);
				});
			};
		}).directive('authDemoApplication', function() {
	return {
		restrict : 'C',
		link : function(scope, elem, attrs) {
			// once Angular is started, remove class:
			elem.removeClass('waiting-for-angular');

			var login = elem.find('#login-holder');
			var main = elem.find('#content');

			login.hide();

			scope.$on('event:auth-loginRequired', function() {
				login.slideDown('slow', function() {
					main.hide();
				});
			});
			scope.$on('event:auth-loginConfirmed', function() {
				main.show();
				login.slideUp();
			});
		}
	}
});
;
