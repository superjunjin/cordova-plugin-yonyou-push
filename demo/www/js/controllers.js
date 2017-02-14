angular.module('starter.controllers', [])

.controller('HomeCtrl', function($scope,$ionicModal,$rootScope,$ionicLoading,$q) {

    // alert("HomeCtrl");
    
    $scope.data = {
        title: '',//标题
        des: ''//描述
    };

    function init(){
      if (window.cordova && cordova.plugins.UPush) {
          cordova.plugins.UPush.init();
      }
    }
    


    document.addEventListener('upush.NotificationReceived', function (e) {
        $rootScope.$broadcast('upush.NotificationReceived', e.upushNotification);
    }, false);

    document.addEventListener("deviceready", function () {
        init();

        $scope.$on('upush.NotificationReceived', function (e, data) {
            alert(data);
            
            var bToObj  = JSON.parse(data);
            
            $scope.data.title = bToObj.title;
            $scope.data.des = bToObj.description;
            
            $scope.$apply();
        });

    }, false);
  
    
    
});
