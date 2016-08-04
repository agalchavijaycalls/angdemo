
angular.module('example').controller('NewExampleController', function ($scope, $location, locationParser, flash, ExampleResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.example = $scope.example || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The example was created successfully.'});
            $location.path('/Examples');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        ExampleResource.save($scope.example, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Examples");
    };
});