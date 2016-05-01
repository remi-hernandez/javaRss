angular
.module('app.home')
.controller('HomeCtrl', HomeController);

HomeController.$inject = ['$rootScope', '$http'];
function HomeController($rootScope, $http) { 
	var _this = this;
	_this.data = null;
	_this.activeObjs = {};
	_this.$http = $http;
	_this.loadRss('data.json');
};

HomeController.prototype.loadRss = function(url) {
	var _this = this;
	console.log('loading : ' + url)
	_this.$http.get(url).success(function (data) {
      _this.data = data[0];
    }).error(function (err) {
    	_this.err = "Désolé, impossible d'ouvrir : " + url;
    });
};

HomeController.prototype.switchActive = function(index) {
	console.log(index);
	var _this = this;
	if (_this.activeObjs[index]) {
		_this.activeObjs[index] = false;
	} else {
		_this.activeObjs[index] = true;
	}

};

HomeController.prototype.isActive = function(index) {
	return this.activeObjs[index] == true;
};

HomeController.prototype.openflux = function (name) {
	var _this = this;
	_this.loadRss('http://oktm.io:4242/' + name);
}

HomeController.prototype.saveflux = function (name, url) {
	console.log(name);
	console.log(url);
	var _this = this;
	_this.$http.post('http://oktm.io:4242/' + name, {remote: url}).success (function (data) {
		console.log(data);
	}).error(function (err) {
    	_this.err = "Désolé, une erreur s'est produite lors de la sauvegarde du flux : " + name;
	});
}