describe('calculateScheduleHeight', function () {
    it('uses the window height, header height and top/bottom padding to ', function () {
        $("<div id=\"header\"></div><div id=\"schedule\"></div>").appendTo("#fixtures");
        $("#header").css("height", 5);
        $("#schedule").css("padding", 10);
        var windowHeight = 500;

        expect(frontlinesms.calculateScheduleHeight(windowHeight)).toEqual(windowHeight - 10 - 10 - 5);
    });
    afterEach(function() {
         $("#header, #schedule").remove();
    });
});

describe('show event details',function(){
    beforeEach(function(){
       var tempHTML =
                "<input type=\"button\" name=\"li\" id=\"test-dialog-button\"/>" +
                        "<div id=\"view-event\" title=\"Event Details\">" +
                        "<label>Title :</label>" +
                        "<input type='text' name=\"eventTitle\" id=\"event-title\" value=''/><br/>" +
                        "<label>Date :</label>" +
                        "<input type='text' name=\"eventDate\" id=\"event-date\" value=''/><br/>" +
                        "<label>Start Time :</label>" +
                        "<input type='text' name=\"eventStartTime\" id=\"event-start-time\" value=''/><br/>" +
                        "<label>End Time :</label>" +
                        "<input type='text' name=\"eventEndTime\" id=\"event-end-time\" value=''/><br/>" +
                        "<input type=\"button\" id=\"update-event\" value=\"Update\"/>" +
                        "<input type=\"button\" id=\"delete-event\" value=\"Delete\"/>" +
                        "<label name=\"eventId\" id=\"event-id\"></label>" +
                        "</div>";
        $(tempHTML).appendTo("#fixtures");
         $(tempHTML).appendTo("#fixtures");
        frontlinesms.eventDetails();
        function EventTemplate() {
            this.title = "test";
            this.start = new Date(2011, 6, 3, 0, 0, 0);
            this.end = new Date(2011, 6, 3, 15, 50, 0);
        }

        ;
        $('#test-dialog-button').click(function() {
            frontlinesms.displayEventDetails(new EventTemplate());
        });
    });

    it('should display event details in pop up',function(){
        $('#test-dialog-button').click();
        expect($('#event-title').val()).toEqual("test");
        expect($('#event-date').val()).toEqual("July 3,2011");
        expect($('#event-start-time').val()).toEqual("12:00AM");
        expect($('#event-end-time').val()).toEqual("03:50PM");
    });

     afterEach(function(){
               $("#view-event, #test-dialog-button").remove();
    });
});
