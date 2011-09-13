var frontlinesms = this.frontlinesms || {};

frontlinesms.caseSearchOnLoad = function() {
    frontlinesms.initializeAutocomplete();
}

frontlinesms.initializeAutocomplete = function() {
    //$("#SearchResults tbody tr").attr('filtermatch','true')
    //$("#SearchResults tbody tr").attr('style','display:true')
    $("#SearchResults").tableFilter({
        additionalFilterTriggers: [$("#caseId")]
    });
    $("#caseId").removeAttr("title").val("").focus();
    $("#SearchResults thead tr.filters").remove();
}




