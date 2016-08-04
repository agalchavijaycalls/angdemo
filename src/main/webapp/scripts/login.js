(function() {
	'use strict';
	angular.module('login', [ 'http-auth-interceptor' ])

	.controller('LoginController', function($scope, $http, authService) {

		$scope.submit = function() {
			$http({
				url : 'rest/users/login',
				method : "POST",
				data : {
					'email' : $scope.username,
					'password' : $scope.password
				}
			}).then(function(response) {
				alert('Login Success ' + response);
				authService.loginConfirmed();
			}, function(response) {
				alert('Login failed ' + response);
			});
		}
	});
})();
