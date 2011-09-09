var frontlinesms = this.frontlinesms || {};

frontlinesms.blockKeyInField = function(keyCode, fieldId) {

    $(fieldId).keypress(function(key) {
            if(keyCode != key.keyCode) {
                return true;
            }
            return false;
    });
};

frontlinesms.enableKeyInField = function(keyCode, fieldId) {

    $(fieldId).keydown(function(key) {
            if(keyCode == key.keyCode) {
                return true;
            }
            return false;
    });
};