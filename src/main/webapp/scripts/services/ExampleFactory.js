angular.module('example').factory('ExampleResource', function($resource){
    var resource = $resource('rest/examples/:ExampleId',{ExampleId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});