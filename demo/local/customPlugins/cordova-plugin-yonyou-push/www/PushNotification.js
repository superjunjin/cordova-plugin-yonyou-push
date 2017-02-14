
var exec = require('cordova/exec');

exports.init = function(){  
   exec(null, null, 'PushNotification', 'init', []);
};



