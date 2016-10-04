'use strict';

describe('Controller Tests', function() {

    describe('Student Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStudent, MockStage, MockTeacher, MockContact;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStudent = jasmine.createSpy('MockStudent');
            MockStage = jasmine.createSpy('MockStage');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockContact = jasmine.createSpy('MockContact');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Student': MockStudent,
                'Stage': MockStage,
                'Teacher': MockTeacher,
                'Contact': MockContact
            };
            createController = function() {
                $injector.get('$controller')("StudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tryApp:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
