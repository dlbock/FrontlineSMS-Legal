var frontlinesms = this.frontlinesms || {};

(function () {

    if (this.console && this.console.log) {
        frontlinesms.log = function () {
            console.log.apply(console, arguments);
        };
    } else {
        frontlinesms.log = function () {
        };
    }

}());
