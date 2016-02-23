describe('navbar', function() {

  beforeEach(module('static'));

  var scope,compile;

  beforeEach(inject(function($rootScope,$compile) {
    scope = $rootScope.$new();
    compile = $compile;
  }));

});