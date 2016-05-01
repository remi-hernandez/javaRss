angular
.module('app')
.filter('timeago', timeagoFilter);

function timeagoFilter(){
	return function (val, locale) {
		locale = locale || 'en';
		var offset = new Date().getTimezoneOffset();
		var time = moment(val).locale(locale).utcOffset(offset).fromNow();
		delete offset;
		return time;
	};
};