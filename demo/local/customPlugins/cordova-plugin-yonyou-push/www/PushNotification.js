
var exec = require('cordova/exec');

exports.init = function(){  
   exec(null, null, 'PushNotification', 'init', []);
};
exports.setDeviceId = function(deviceId){  
   exec(null, null, 'PushNotification', 'setDeviceId', [deviceId]);
};

exports.startService = function(){  
   exec(null, null, 'PushNotification', 'startService', []);
};

exports.stopService = function(){  
   exec(null, null, 'PushNotification', 'stopService', []);
};



